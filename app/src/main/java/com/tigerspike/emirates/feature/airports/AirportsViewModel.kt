package com.tigerspike.emirates.feature.airports

import android.arch.lifecycle.*
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import android.support.annotation.RequiresApi
import com.tigerspike.emirates.tools.extensions.save
import com.tigerspike.emirates.tools.networking.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Function


class AirportsViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val dao = provideAirportDb(context.applicationContext).dao()
        val service = newAirportService()
        return AirportsViewModel(PreferenceManager.getDefaultSharedPreferences(context.applicationContext), dao, service) as T
    }
}

private const val k_airportsSynced = "airportsSynced"

class AirportsViewModel(
        private val sharedPreferences: SharedPreferences,
        private val airportDb: AirportDao,
        private val service: AirportService,
        val isProgress: MutableLiveData<Boolean> = MutableLiveData(),
        val error: MutableLiveData<String> = MutableLiveData()
) : ViewModel() {

    private val filter: MutableLiveData<String> = MutableLiveData()

    init {
        if (!sharedPreferences.getBoolean(k_airportsSynced, false)) {
            refreshAirports()
        }
    }

    val airports: LiveData<Array<Airport>> =
            Transformations.switchMap(filter, @RequiresApi(Build.VERSION_CODES.N)
            object : Function<String, LiveData<Array<Airport>>>, android.arch.core.util.Function<String, LiveData<Array<Airport>>> {
                override fun apply(filter: String): LiveData<Array<Airport>> {
                    val query = getConditionalQuery(filter)
                    return airportDb.query()
                }

            })

    fun getAirports(filter: String = "") {
        this.filter.postValue(filter)
    }

    private fun refreshAirports() {
        isProgress.postValue(true)
        service.getAirports(endpoint_debugAirports, "en_US")
                .enqueue(object : Callback<ApiResponse<Array<Airport>>> {
                    override fun onResponse(call: Call<ApiResponse<Array<Airport>>>?, response: Response<ApiResponse<Array<Airport>>>?) {
                        isProgress.postValue(false)
                        error.postValue("")
                        Thread(Runnable {
                            val body = response?.body()
                            val airports = body?.payload ?: emptyArray()
                            airports.sortAirports()
                            airportDb.insert(airports)
                            sharedPreferences.save {
                                putBoolean(k_airportsSynced, true)
                            }
                        }).start()
                    }

                    override fun onFailure(call: Call<ApiResponse<Array<Airport>>>?, t: Throwable?) {
                        isProgress.postValue(false)
                        error.postValue(t.toString())
                    }
                })
    }

}

private fun Array<Airport>.sortAirports() {
    sortWith(Comparator { left, right ->
        when {
            left.name ?: "" > right.name ?: "" -> 1
            left.name == right.name -> 0
            else -> -1
        }
    })
}

private fun getConditionalQuery(filter: String): AirportDao.() -> LiveData<Array<Airport>> =
        if (filter.isEmpty()) {
            { fetchAll() }
        } else {
            { fetchAllMatching("%$filter%") }
        }




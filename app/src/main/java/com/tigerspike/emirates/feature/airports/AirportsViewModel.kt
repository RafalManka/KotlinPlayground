package com.tigerspike.emirates.feature.airports

import android.arch.lifecycle.*
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Function


class AirportsViewModelFactory(
        private val context: Context,
        private val lifecycleOwner: LifecycleOwner
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val dao = provideAirportDb(context).dao()
        val service = newAirportService()
        return AirportsViewModel(lifecycleOwner, dao, service) as T
    }
}

class AirportsViewModel(
        lifecycleOwner: LifecycleOwner,
        private val airportDb: AirportDao,
        private val service: AirportService,
        val isProgress: MutableLiveData<Boolean> = MutableLiveData(),
        val error: MutableLiveData<String> = MutableLiveData()
) : ViewModel() {

    private val filter: MutableLiveData<String> = MutableLiveData()

    init {
        airportDb.count().observe(lifecycleOwner, Observer {
            if (it == 0) {
                refreshAirports()
            }
        })
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
        service.getAirports()
                .enqueue(object : Callback<Array<Airport>> {
                    override fun onResponse(call: Call<Array<Airport>>?, response: Response<Array<Airport>>?) {
                        isProgress.postValue(false)
                        error.postValue("")
                        Thread(Runnable {
                            val airports = response
                                    ?.body()
                                    ?.removeInvalidAirports()
                                    ?: emptyArray()
                            airports.sortAirports()
                            airportDb.insert(airports)
                        }).start()
                    }

                    override fun onFailure(call: Call<Array<Airport>>?, t: Throwable?) {
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

private fun Array<Airport>.removeInvalidAirports(): Array<Airport> =
        filter {
            it.name?.isNotBlank() == true
                    && !it.name.contains("?")
                    && it.type == "airport"
                    && (it.size == "large" || it.size == "medium")
        }.toTypedArray()




package com.tigerspike.emirates.feature.airports

import android.arch.lifecycle.*
import android.content.Context
import com.tigerspike.emirates.tools.extensions.containsIgnoreCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AirportsViewModelFactory(private val context: Context, private val lifecycleOwner: LifecycleOwner) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AirportsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AirportsViewModel(lifecycleOwner, provideAirportDb(context), newAirportService()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

class AirportsViewModel(
        private val lifecycleOwner: LifecycleOwner,
        private val airportDb: AirportDb,
        private val service: AirportService,
        val isProgress: MutableLiveData<Boolean> = MutableLiveData(),
        val error: MutableLiveData<String> = MutableLiveData(),
        val airports: MutableLiveData<Array<Airport>> = MutableLiveData()
) : ViewModel() {

    private var filterBy: String = ""
    private var isRunning: Boolean = false

    fun getAirports(filter: String = "") {
        filterBy = filter
        airportDb.dao()
                .fetchAll()
                .observe(lifecycleOwner, Observer {
                    if (it?.isEmpty() == true) {
                        refreshAirports {
                            getAirports(filterBy)
                        }
                    } else {
                        val validator: (Airport?) -> (Boolean) = {
                            it?.code?.containsIgnoreCase(filterBy) == true
                                    || it?.name.containsIgnoreCase(filterBy) == true
                        }
                        val value = it?.filter(validator)?.toTypedArray() ?: emptyArray()
                        airports.postValue(value)
                    }
                })

    }

    private fun refreshAirports(onSuccess: () -> Unit) {
        if (isRunning) {
            return
        }
        isRunning = true
        isProgress.postValue(true)
        service.getAirports()
                .enqueue(object : Callback<Array<Airport>> {
                    override fun onResponse(call: Call<Array<Airport>>?, response: Response<Array<Airport>>?) {
                        Thread(Runnable {
                            isProgress.postValue(false)
                            error.postValue("")
                            val filter: (Airport) -> Boolean = {
                                it.name?.isNotBlank() == true
                                        && !it.name.contains("?")
                                        && it.type == "airport"
                                        && (it.size == "large" || it.size == "medium")
                            }
                            val airports = response?.body()
                                    ?.filter(filter)
                                    ?.toTypedArray() ?: emptyArray()
                            airports.sortWith(Comparator { left, right ->
                                when {
                                    left.name ?: "" > right.name ?: "" -> 1
                                    left.name == right.name -> 0
                                    else -> -1
                                }
                            })
                            airportDb.dao().insert(airports)
                            isRunning = false
                            onSuccess()
                        }).start()
                    }

                    override fun onFailure(call: Call<Array<Airport>>?, t: Throwable?) {
                        isProgress.postValue(false)
                        error.postValue(t.toString())
                        isRunning = false
                    }

                })
    }
}



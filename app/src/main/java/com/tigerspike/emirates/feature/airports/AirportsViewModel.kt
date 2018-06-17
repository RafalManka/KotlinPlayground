package com.tigerspike.emirates.feature.airports

import android.arch.lifecycle.*
import android.content.Context
import com.tigerspike.emirates.tools.extensions.containsIgnoreCase
import com.tigerspike.emirates.tools.extensions.logDebug
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AirportsViewModelFactory(private val context: Context, private val lifecycleOwner: LifecycleOwner) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AirportsViewModel::class.java)) {
            return AirportsViewModel(lifecycleOwner, AirportPersistence(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

class AirportsViewModel(
        private val lifecycleOwner: LifecycleOwner,
        private val persistence: AirportPersistence,
        val isProgress: MutableLiveData<Boolean> = MutableLiveData(),
        val error: MutableLiveData<String> = MutableLiveData(),
        val airports: MutableLiveData<Array<Airport>> = MutableLiveData(),
        private val service: AirportService = newAirportService()
) : ViewModel() {

    private var filterBy: String = ""
    private var isRunning: Boolean = false

    fun getAirports(filter: String = "") {
        filterBy = filter
        persistence.movieDatabase.dao().fetchAll().observe(lifecycleOwner, Observer {
            if (it?.isEmpty() == true) {
                refreshAirports {
                    getAirports(filterBy)
                }
            } else {
                val validator: (Airport?) -> (Boolean) = {
                    it?.code?.containsIgnoreCase(filterBy) == true || it?.name.containsIgnoreCase(filterBy) == true
                }
                airports.postValue(it?.filter { validator(it) }?.toTypedArray() ?: emptyArray())
            }
        })

    }

    private fun refreshAirports(onSuccess: () -> Unit) {
        if (isRunning) {
            return
        }
        isRunning = true
        isProgress.postValue(true)
        service.getAirports().enqueue(object : Callback<Array<Airport>> {
            override fun onResponse(call: Call<Array<Airport>>?, response: Response<Array<Airport>>?) {
                Thread(Runnable {
                    isProgress.postValue(false)
                    error.postValue("")

                    val airports = response?.body()?.filter {
                        it.name?.logDebug()
                        it.name?.isNotBlank() == true && it.name.contains("?") == false && it.type == "airport" && (it.size == "large" || it.size == "medium")
                    }?.toTypedArray() ?: emptyArray()

                    airports.sortWith(Comparator { left, right ->
                        when {
                            left.name ?: "" > right.name ?: "" -> 1
                            left.name == right.name -> 0
                            else -> -1
                        }
                    })
                    persistence.movieDatabase.dao().insert(airports)
                    onSuccess()
                    isRunning = false
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



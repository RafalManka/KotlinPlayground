package com.tigerspike.emirates.feature.airports

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tigerspike.emirates.tools.extensions.containsIgnoreCase
import com.tigerspike.emirates.tools.extensions.logDebug
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirportsViewModel(
        val isProgress: MutableLiveData<Boolean> = MutableLiveData(),
        val error: MutableLiveData<String> = MutableLiveData(),
        val airports: MutableLiveData<Array<Airport>> = MutableLiveData(),
        private val persistence: AirportPersistence = AirportPersistence,
        private val service: AirportService = newAirportService()
) : ViewModel() {

    private var filterBy: String = ""
    private var isRunning: Boolean = false

    fun getAirports(filter: String = "") {
        filterBy = filter
        if (persistence.airports == null) {
            refreshAirports {
                getAirports(filterBy)
            }
        } else {
            val validator: (Airport?) -> (Boolean) = {
                it?.code?.containsIgnoreCase(filterBy) == true || it?.name.containsIgnoreCase(filterBy) == true
            }
            airports.postValue(persistence.airports?.filter { validator(it) }?.toTypedArray()
                    ?: emptyArray())
        }
    }

    private fun refreshAirports(onSuccess: () -> Unit) {
        if (isRunning) {
            return
        }
        isRunning = true
        isProgress.postValue(true)
        service.getAirports().enqueue(object : Callback<Array<Airport>> {
            override fun onResponse(call: Call<Array<Airport>>?, response: Response<Array<Airport>>?) {
                isProgress.postValue(false)
                error.postValue("")
                persistence.airports = response?.body()?.filter {
                    it.name?.logDebug()
                    it.name?.isNotBlank() == true && it.name.contains("?") == false && it.type == "airport" && (it.size == "large" || it.size == "medium")
                }?.toTypedArray() ?: emptyArray()
                persistence.airports?.sortWith(Comparator { left, right ->
                    when {
                        left.name ?: "" > right.name ?: "" -> 1
                        left.name == right.name -> 0
                        else -> -1
                    }
                })
                onSuccess()
                isRunning = false
            }

            override fun onFailure(call: Call<Array<Airport>>?, t: Throwable?) {
                isProgress.postValue(false)
                error.postValue(t.toString())
                isRunning = false
            }

        })
    }
}



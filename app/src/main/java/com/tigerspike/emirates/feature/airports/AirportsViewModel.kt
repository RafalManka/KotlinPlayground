package com.tigerspike.emirates.feature.airports

import android.arch.lifecycle.*
import android.content.Context
import com.tigerspike.emirates.tools.extensions.logWarning
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        private val lifecycleOwner: LifecycleOwner,
        private val airportDb: AirportDao,
        private val service: AirportService,
        val isProgress: MutableLiveData<Boolean> = MutableLiveData(),
        val error: MutableLiveData<String> = MutableLiveData(),
        val airports: MutableLiveData<Array<Airport>> = MutableLiveData()
) : ViewModel() {

    private var filterBy: String = ""
    private var isRunning: Boolean = false

    fun getAirports(filter: String = "") {
        filterBy = filter
        airportDb.count()
                .observe(lifecycleOwner, Observer { count ->
                    if (count == 0) {
                        refreshAirports({ getAirports(filterBy) }, { it?.logWarning() })
                    } else {
                        val query = getConditionalQuery(filter)
                        airportDb
                                .query()
                                .observe(lifecycleOwner, Observer {
                                    airports.postValue(it)
                                })
                    }
                })
    }

    private fun refreshAirports(onSuccess: (Array<Airport>) -> Unit, onError: (Throwable?) -> Unit) {
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
                            val airports = response
                                    ?.body()
                                    ?.removeInvalidAirports()
                                    ?: emptyArray()
                            airports.sortAirports()
                            airportDb.insert(airports)
                            isRunning = false
                            onSuccess(airports)
                        }).start()
                    }

                    override fun onFailure(call: Call<Array<Airport>>?, t: Throwable?) {
                        isProgress.postValue(false)
                        error.postValue(t.toString())
                        isRunning = false
                        onError(t)
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




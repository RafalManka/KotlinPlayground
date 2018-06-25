package com.tigerspike.emirates.feature.booking

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tigerspike.emirates.feature.airports.Airport

class BookingViewModel(
        val booking: MutableLiveData<Booking> = MutableLiveData()
) : ViewModel() {
    fun search(from: Airport, to: Airport) {
        booking.postValue(Booking("RAFMAN123"))
    }
}
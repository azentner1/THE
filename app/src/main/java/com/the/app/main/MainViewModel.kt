package com.the.app.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.the.app.common.model.LocationData
import com.the.app.common.repo.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    fun initLocationUpdates() {
        locationRepository.initLocationUpdates()
    }

    fun areLocationPermissionsGranted(): Boolean {
        return locationRepository.areLocationPermissionsGranted()
    }

    fun onLocationChanged() = liveData {
        emitSource(locationRepository.onLocationChanged())
    }

}
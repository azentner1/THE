package com.the.app.common.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.the.app.common.model.LocationData
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationDataSource: LocationDataSource
) {

    fun areLocationPermissionsGranted(): Boolean {
        return locationDataSource.hasLocationPermissions()
    }

    fun initLocationUpdates() {
        locationDataSource.initLocationUpdates()
    }

    suspend fun onLocationChanged() = liveData {
        emitSource(locationDataSource.onLocationChanged())
    }
}
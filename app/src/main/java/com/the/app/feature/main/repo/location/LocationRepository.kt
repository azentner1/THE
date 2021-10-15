package com.the.app.feature.main.repo.location

import com.the.app.feature.main.data.location.LocationDataSource
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

    fun onLocationChanged() = locationDataSource.onLocationChanged()
}
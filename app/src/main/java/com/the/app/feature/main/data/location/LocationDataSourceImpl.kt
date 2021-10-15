package com.the.app.feature.main.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationDataSourceImpl @Inject constructor(
    @ApplicationContext val context: Context
) : LocationDataSource {

    private var isTrackingLocation = false

    private var currentLocation = MutableLiveData<Location>()

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            handleLocationResult(locationResult)
        }
    }

    private fun handleLocationResult(locationResult: LocationResult?) {
        locationResult ?: return
        currentLocation.value = locationResult.lastLocation
    }

    override fun onLocationChanged(): LiveData<Location> {
        return currentLocation
    }

    override fun initLocationUpdates() {
        if (!isTrackingLocation) {
            startLocationUpdates()
            isTrackingLocation = true
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            buildLocationRequest(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun hasLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun buildLocationRequest() = LocationRequest.create().apply {
        interval = LOCATION_INTERVAL
        fastestInterval = FASTEST_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime = MAX_WAIT_TIME
    }

    companion object {
        private const val LOCATION_INTERVAL = 5000L
        private const val FASTEST_INTERVAL = 1000L
        private const val MAX_WAIT_TIME = 1000L
    }
}
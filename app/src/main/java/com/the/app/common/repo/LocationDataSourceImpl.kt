package com.the.app.common.repo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.android.gms.location.*
import com.the.app.common.data.DataResult
import com.the.app.common.mapper.toViewModel
import com.the.app.common.model.LocationData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationDataSourceImpl @Inject constructor(
    @ApplicationContext val context: Context
) : LocationDataSource {

    private var isTrackingLocation = false

    private var currentLocation = MutableLiveData<LocationData>()

    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            handleLocationResult(locationResult)
        }
    }

    private fun handleLocationResult(locationResult: LocationResult?) {
        locationResult ?: return
        currentLocation.value = locationResult.lastLocation.toViewModel()
    }

    override suspend fun onLocationChanged() : LiveData<LocationData> {
        return withContext(Dispatchers.Main) {
            currentLocation
        }
    }

    override fun initLocationUpdates()  {
        if (!isTrackingLocation) {
            startLocationUpdates()
            isTrackingLocation = true
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(buildLocationRequest(), locationCallback, Looper.getMainLooper())
    }

    override fun hasLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun buildLocationRequest() = LocationRequest.create().apply {
        interval = 5000
        fastestInterval = 1000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime = 1000
    }
}
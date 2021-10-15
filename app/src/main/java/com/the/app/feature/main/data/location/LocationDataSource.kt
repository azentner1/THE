package com.the.app.feature.main.data.location

import android.location.Location
import androidx.lifecycle.LiveData

interface LocationDataSource {
    fun initLocationUpdates()
    fun hasLocationPermissions(): Boolean
    fun onLocationChanged(): LiveData<Location>
}
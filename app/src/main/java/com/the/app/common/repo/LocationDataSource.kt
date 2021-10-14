package com.the.app.common.repo

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.the.app.common.data.DataResult
import com.the.app.common.model.LocationData

interface LocationDataSource {
    fun initLocationUpdates()
    fun hasLocationPermissions(): Boolean
    suspend fun onLocationChanged(): LiveData<LocationData>
}
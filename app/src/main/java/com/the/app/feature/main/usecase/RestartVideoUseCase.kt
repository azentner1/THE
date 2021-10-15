package com.the.app.feature.main.usecase

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.the.app.feature.main.model.LocationData
import com.the.app.feature.main.repo.location.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestartVideoUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    private var currentLocationData = MutableLiveData<LocationData>()

    private var lastLocation: Location? = null

    init {
        locationRepository.onLocationChanged().observeForever(Observer {
            handleValue(it)
        })
    }

    suspend fun onRestartVideo(): LiveData<LocationData> {
        return withContext(Dispatchers.Main) {
            currentLocationData
        }
    }

    private fun handleValue(location: Location) {
        val distance: Float = lastLocation?.distanceTo(location) ?: 0.0f
        lastLocation = location
        currentLocationData.value = LocationData(
            changed = distance > LOCATION_THRESHOLD
        )
    }

    companion object {
        private const val LOCATION_THRESHOLD = 10
    }
}
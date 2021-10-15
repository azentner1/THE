package com.the.app.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.the.app.feature.main.repo.location.LocationRepository
import com.the.app.feature.main.usecase.ChangeVideoTimeUseCase
import com.the.app.feature.main.usecase.ChangeVideoVolumeUseCase
import com.the.app.feature.main.usecase.PauseVideoUseCase
import com.the.app.feature.main.usecase.RestartVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val changeVideoTimeUseCase: ChangeVideoTimeUseCase,
    private val changeVideoVolumeUseCase: ChangeVideoVolumeUseCase,
    private val pauseVideoUseCase: PauseVideoUseCase,
    private val restartVideoUseCase: RestartVideoUseCase
) : ViewModel() {

    fun areLocationPermissionsGranted(): Boolean {
        return locationRepository.areLocationPermissionsGranted()
    }

    fun initLocationUpdates() {
        locationRepository.initLocationUpdates()
    }

    fun onRestartVideo() = liveData {
        emitSource(restartVideoUseCase.onRestartVideo())
    }

    fun onChangeVideoVolume() = liveData {
        emitSource(changeVideoVolumeUseCase.onChangeVideoVolume())
    }

    fun onChangeVideoTime() = liveData {
        emitSource(changeVideoTimeUseCase.onChangeVideoTime())
    }

    fun onPauseVideo() = liveData {
        emitSource(pauseVideoUseCase.onPauseVideo())
    }
}
package com.the.app.common.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.the.app.common.model.GyroData
import javax.inject.Inject

class GyroRepository @Inject constructor(private val gyroDataSource: GyroDataSource) {

    fun initGyroSensor() {
        gyroDataSource.initGyroSensor()
    }

    suspend fun onGyroDataChanged() = liveData {
        emitSource(gyroDataSource.onGyroDataChanged())
    }
}
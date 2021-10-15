package com.the.app.feature.main.repo.gyro

import com.the.app.feature.main.data.gyro.GyroDataSource
import javax.inject.Inject

class GyroRepository @Inject constructor(private val gyroDataSource: GyroDataSource) {

    fun initGyroSensor() {
        gyroDataSource.initGyroSensor()
    }

    fun onGyroDataChanged() = gyroDataSource.onGyroDataChanged()
}
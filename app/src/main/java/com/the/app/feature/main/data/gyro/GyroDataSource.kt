package com.the.app.feature.main.data.gyro

import androidx.lifecycle.LiveData
import com.the.app.feature.main.model.GyroData

interface GyroDataSource {
    fun initGyroSensor()
    fun onGyroDataChanged(): LiveData<GyroData>
}
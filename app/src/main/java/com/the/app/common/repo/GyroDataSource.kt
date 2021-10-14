package com.the.app.common.repo

import androidx.lifecycle.LiveData
import com.the.app.common.model.GyroData

interface GyroDataSource {
    fun initGyroSensor()
    suspend fun onGyroDataChanged(): LiveData<GyroData>
}
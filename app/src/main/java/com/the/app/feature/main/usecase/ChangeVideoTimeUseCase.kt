package com.the.app.feature.main.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.the.app.feature.main.model.GyroData
import com.the.app.feature.main.model.GyroXData
import com.the.app.feature.main.repo.gyro.GyroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

class ChangeVideoTimeUseCase @Inject constructor(
    private val gyroRepository: GyroRepository
) {

    init {
        gyroRepository.initGyroSensor()
        gyroRepository.onGyroDataChanged().observeForever(Observer {
            handleValue(it)
        })
    }

    private val currentGyroData = MutableLiveData<GyroXData>()

    suspend fun onChangeVideoTime(): LiveData<GyroXData> {
        return withContext(Dispatchers.Main) {
            currentGyroData
        }
    }

    private fun handleValue(data: GyroData) {
        if (data is GyroXData && data.value.roundToInt() != 0) {
            currentGyroData.value = data
        }
    }
}
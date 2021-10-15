package com.the.app.feature.main.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.the.app.feature.main.model.ShakeData
import com.the.app.feature.main.repo.shake.ShakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PauseVideoUseCase @Inject constructor(
    private val shakeRepository: ShakeRepository
) {

    private val currentShakeData = MutableLiveData<ShakeData>()

    init {
        shakeRepository.initShakeSensor()
        shakeRepository.onShakeDataChanged().observeForever(Observer {
            handleValue(it)
        })
    }

    suspend fun onPauseVideo(): LiveData<ShakeData> {
        return withContext(Dispatchers.Main) {
            currentShakeData
        }
    }

    private fun handleValue(value: Float) {
        if (value > SHAKE_THRESHOLD) {
           currentShakeData.value = ShakeData(
               shake = true
           )
        }
    }

    companion object {
        private const val SHAKE_THRESHOLD = 12
    }
}
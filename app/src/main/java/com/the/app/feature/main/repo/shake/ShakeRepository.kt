package com.the.app.feature.main.repo.shake

import com.the.app.feature.main.data.shake.ShakeDataSource
import javax.inject.Inject

class ShakeRepository @Inject constructor(
    private val shakeDataSource: ShakeDataSource
) {

    fun initShakeSensor() {
        shakeDataSource.initShakeSensor()
    }

    fun onShakeDataChanged() = shakeDataSource.onShakeDataChanged()
}
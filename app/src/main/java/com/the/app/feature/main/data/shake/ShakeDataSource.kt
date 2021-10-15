package com.the.app.feature.main.data.shake

import androidx.lifecycle.LiveData

interface ShakeDataSource {
    fun initShakeSensor()
    fun onShakeDataChanged(): LiveData<Float>
}
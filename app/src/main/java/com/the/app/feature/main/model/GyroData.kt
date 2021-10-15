package com.the.app.feature.main.model

interface GyroData

data class GyroXData(val value: Float = 0.0f): GyroData
data class GyroZData(val value: Float = 0.0f): GyroData
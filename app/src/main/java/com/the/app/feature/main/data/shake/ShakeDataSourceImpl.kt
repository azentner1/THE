package com.the.app.feature.main.data.shake

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.sqrt

class ShakeDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ShakeDataSource, SensorEventListener {

    private val currentShakeData = MutableLiveData<Float>()

    private var lastAccelerationValue = SensorManager.GRAVITY_EARTH

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    override fun initShakeSensor() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
    }

    override fun onShakeDataChanged(): LiveData<Float> {
        return currentShakeData
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor ?: return
        if (sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val currentAcceleration = sqrt(x * x + y * y + z * z)
            val delta = currentAcceleration - lastAccelerationValue
            val acceleration = 10f * 0.0f + delta
            currentShakeData.value = acceleration
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

}
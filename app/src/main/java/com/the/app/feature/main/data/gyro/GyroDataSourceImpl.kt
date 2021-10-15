package com.the.app.feature.main.data.gyro

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.the.app.feature.main.model.GyroData
import com.the.app.feature.main.model.GyroXData
import com.the.app.feature.main.model.GyroZData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GyroDataSourceImpl @Inject constructor(
    @ApplicationContext val context: Context
) : GyroDataSource, SensorEventListener {

    private val currentGyroData = MutableLiveData<GyroData>()

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    override fun initGyroSensor() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
    }

    override fun onGyroDataChanged(): LiveData<GyroData> {
        return currentGyroData
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor ?: return
        if (sensor.type == Sensor.TYPE_GYROSCOPE) {
            val x = event.values[0]
            val z = event.values[2]
            currentGyroData.value = GyroXData(
                value = -x
            )
            currentGyroData.value = GyroZData(
                value = z
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
package com.the.app.common.mapper

import android.location.Location
import com.the.app.common.model.LocationData

fun Location.toViewModel() : LocationData {
    return LocationData(
        latitude = latitude,
        longitude = longitude
    )
}
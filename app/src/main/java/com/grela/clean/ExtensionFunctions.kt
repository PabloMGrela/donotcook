package com.grela.clean

import android.location.Location

fun Location.getDistanceString(currentPosition: Location): String {
    return "${"%.2f".format(this.getDistance(currentPosition))} km"
}

fun Location.getDistance(currentPosition: Location): Float {
    val result = FloatArray(1)
    Location.distanceBetween(
        this.latitude,
        this.longitude,
        currentPosition.latitude,
        currentPosition.longitude,
        result
    )
    return result[0] / 1000
}
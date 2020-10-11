package com.grela.clean

import android.location.Location
import com.google.android.libraries.maps.model.LatLng

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

fun LatLng.getDistance(currentPosition: LatLng): Float {
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

fun LatLng.getDistanceString(currentPosition: LatLng): String {
    return "${"%.2f".format(this.getDistance(currentPosition))} km"
}

fun List<String>.toStringList(): String {
    var text = ""
    this.forEach {
        text += "$it\n"
    }
    return text
}
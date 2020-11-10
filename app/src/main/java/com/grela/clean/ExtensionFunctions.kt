package com.grela.clean

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.util.Base64
import android.util.Base64.encodeToString
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.libraries.maps.model.LatLng
import java.io.ByteArrayOutputStream

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

fun LatLng?.getDistanceString(currentPosition: LatLng): String {
    return this?.let {
        "${"%.2f".format(this.getDistance(currentPosition))} km"
    } ?: "-"
}

fun List<String>.toStringList(): String {
    var text = ""
    this.forEach {
        text += "$it\n"
    }
    return text
}

fun View.setSingleClickListener(l: View.OnClickListener?) {
    setOnClickListener(l?.let {
        OnClickListenerSingleClickWrapper(l)
    })
}

fun View.setSingleClickListener(func: ((View) -> Unit)?) {
    setOnClickListener(func?.let {
        OnClickListenerSingleClickWrapper(View.OnClickListener(func))
    })
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Bitmap.bitMapToString(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return encodeToString(b, Base64.DEFAULT)
}

fun String.toBitmap(): Bitmap {
    val imageBytes = Base64.decode(this, 0)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

class OnClickListenerSingleClickWrapper(private val inner: View.OnClickListener) :
    View.OnClickListener {
    companion object {
        private const val DEFAULT_INTERVAL = 750
    }

    private var lastClickTime = 0L

    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= DEFAULT_INTERVAL) {
            inner.onClick(v)
            lastClickTime = currentTime
        }
    }
}
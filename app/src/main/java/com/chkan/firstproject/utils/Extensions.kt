package com.chkan.firstproject.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}

fun String.toLatLng(text: String) : LatLng {

    val latlong = text.split(",").toTypedArray()
    val latitude = latlong[0].toDouble()
    val longitude = latlong[1].toDouble()

    return LatLng(latitude, longitude)

}
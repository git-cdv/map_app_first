package com.chkan.firstproject.data.cash

import android.util.Log
import com.chkan.firstproject.data.network.model.autocomplete.Prediction

object CashDataSource {
    private val placesMutableList: MutableList<Prediction> = mutableListOf()

    var places: List<Prediction>
        get() = placesMutableList
        set(value) {
            placesMutableList.clear()
            placesMutableList.addAll(value)
            Log.d("MYAPP", "CashDataSource - places: $placesMutableList")
        }
}
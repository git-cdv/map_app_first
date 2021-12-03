package com.chkan.data.sources.cash

import com.chkan.data.sources.network.model.autocomplete.Prediction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CashDataSource @Inject constructor() {
    private val placesMutableList: MutableList<Prediction> = mutableListOf()

    var places: List<Prediction>
        get() = placesMutableList
        set(value) {
            placesMutableList.clear()
            placesMutableList.addAll(value)
        }
}
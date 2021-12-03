package com.chkan.domain.models

import com.chkan.data.sources.local.LocalModel

data class LocalModelUI(val name:String, val latlng: String?)

fun ArrayList<LocalModel>.asLocalModelUI(): List<LocalModelUI> {
    return this.map {
        LocalModelUI(
            name = it.name,
            latlng = it.latlng)
    }
}
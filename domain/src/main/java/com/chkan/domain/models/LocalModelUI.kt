package com.chkan.domain.models

import com.chkan.data.sources.local.DatabaseModel

data class LocalModelUI(val name:String, val latlng: String?)

fun List<DatabaseModel>.asLocalModelUI(): List<LocalModelUI> {
    return this.map {
        LocalModelUI(
            name = it.name,
            latlng = it.latlng)
    }
}
package com.chkan.data.sources.local

import kotlinx.serialization.Serializable

@Serializable
data class LocalModel(val name:String, val latlng: String?)


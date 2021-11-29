package com.chkan.firstproject.data.local

import kotlinx.serialization.Serializable

@Serializable
data class LocalModel(val name:String, val latlng: String)

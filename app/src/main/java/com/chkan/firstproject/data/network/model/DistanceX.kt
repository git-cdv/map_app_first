package com.chkan.firstproject.data.network.model


import com.squareup.moshi.Json

data class DistanceX(
    @Json(name = "text")
    val text: String,
    @Json(name = "value")
    val value: Int
)
package com.chkan.firstproject.data.network.model


import com.squareup.moshi.Json

data class Northeast(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lng")
    val lng: Double
)
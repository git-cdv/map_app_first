package com.chkan.firstproject.data.network.model


import com.squareup.moshi.Json

data class Bounds(
    @Json(name = "northeast")
    val northeast: Northeast,
    @Json(name = "southwest")
    val southwest: Southwest
)
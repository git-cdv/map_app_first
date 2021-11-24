package com.chkan.firstproject.data.network.model


import com.squareup.moshi.Json

data class OverviewPolyline(
    @Json(name = "points")
    val points: String
)
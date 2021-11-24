package com.chkan.firstproject.data.network.model


import com.squareup.moshi.Json

data class GeocodedWaypoint(
    @Json(name = "geocoder_status")
    val geocoderStatus: String,
    @Json(name = "place_id")
    val placeId: String,
    @Json(name = "types")
    val types: List<String>
)
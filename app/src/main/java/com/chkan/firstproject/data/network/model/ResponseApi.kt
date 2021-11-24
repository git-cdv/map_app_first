package com.chkan.firstproject.data.network.model


import com.squareup.moshi.Json

data class ResponseApi(
    @Json(name = "geocoded_waypoints")
    val geocodedWaypoints: List<GeocodedWaypoint>,
    @Json(name = "routes")
    val routes: List<Route>,
    @Json(name = "status")
    val status: String
)
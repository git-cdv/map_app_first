package com.chkan.firstproject.data.network.model

data class ResponseApi(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
)
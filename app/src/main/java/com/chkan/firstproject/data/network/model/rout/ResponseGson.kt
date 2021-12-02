package com.chkan.firstproject.data.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGson(
    @SerialName("geocoded_waypoints")
    val geocodedWaypoints: List<GeocodedWaypoint>,
    @SerialName("routes")
    val routes: List<Route>,
    @SerialName("status")
    val status: String
)
package com.chkan.firstproject.data.network.model


import com.google.gson.annotations.SerializedName

data class ResponseGson(
    @SerializedName("geocoded_waypoints")
    val geocodedWaypoints: List<GeocodedWaypoint>,
    @SerializedName("routes")
    val routes: List<Route>,
    @SerializedName("status")
    val status: String
)
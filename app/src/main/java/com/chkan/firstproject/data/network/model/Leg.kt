package com.chkan.firstproject.data.network.model


import com.squareup.moshi.Json

data class Leg(
    @Json(name = "distance")
    val distance: Distance,
    @Json(name = "duration")
    val duration: Duration,
    @Json(name = "end_address")
    val endAddress: String,
    @Json(name = "end_location")
    val endLocation: EndLocation,
    @Json(name = "start_address")
    val startAddress: String,
    @Json(name = "start_location")
    val startLocation: StartLocation,
    @Json(name = "steps")
    val steps: List<Step>,
    @Json(name = "traffic_speed_entry")
    val trafficSpeedEntry: List<Any>,
    @Json(name = "via_waypoint")
    val viaWaypoint: List<Any>
)
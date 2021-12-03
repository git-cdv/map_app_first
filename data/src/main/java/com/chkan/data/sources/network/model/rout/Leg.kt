package com.chkan.data.sources.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Leg(
    @SerialName("distance")
    val distance: Distance,
    @SerialName("duration")
    val duration: Duration,
    @SerialName("end_address")
    val endAddress: String,
    @SerialName("end_location")
    val endLocation: EndLocation,
    @SerialName("start_address")
    val startAddress: String,
    @SerialName("start_location")
    val startLocation: StartLocation,
    @SerialName("steps")
    val steps: List<Step>
    /*@SerialName("traffic_speed_entry")
    val trafficSpeedEntry: List<Any>,
    @SerialName("via_waypoint")
    val viaWaypoint: List<Any>*/
)

//TODO: костыль - Any
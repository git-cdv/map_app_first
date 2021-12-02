package com.chkan.firstproject.data.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step(
    @SerialName("distance")
    val distance: DistanceX,
    @SerialName("duration")
    val duration: DurationX,
    @SerialName("end_location")
    val endLocation: EndLocationX,
    @SerialName("html_instructions")
    val htmlInstructions: String,
    @SerialName("maneuver")
    val maneuver: String? = null,
    @SerialName("polyline")
    val polyline: Polyline,
    @SerialName("start_location")
    val startLocation: StartLocationX,
    @SerialName("travel_mode")
    val travelMode: String
)

//TODO: костыль - val maneuver: String? = null
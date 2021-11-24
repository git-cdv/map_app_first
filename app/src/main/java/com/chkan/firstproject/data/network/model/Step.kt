package com.chkan.firstproject.data.network.model


import com.squareup.moshi.Json

data class Step(
    @Json(name = "distance")
    val distance: DistanceX,
    @Json(name = "duration")
    val duration: DurationX,
    @Json(name = "end_location")
    val endLocation: EndLocationX,
    @Json(name = "html_instructions")
    val htmlInstructions: String,
    @Json(name = "maneuver")
    val maneuver: String,
    @Json(name = "polyline")
    val polyline: Polyline,
    @Json(name = "start_location")
    val startLocation: StartLocationX,
    @Json(name = "travel_mode")
    val travelMode: String
)
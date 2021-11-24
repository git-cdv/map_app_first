package com.chkan.firstproject.data.network.model


import com.squareup.moshi.Json

data class Route(
    @Json(name = "bounds")
    val bounds: Bounds,
    @Json(name = "copyrights")
    val copyrights: String,
    @Json(name = "legs")
    val legs: List<Leg>,
    @Json(name = "overview_polyline")
    val overviewPolyline: OverviewPolyline,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "warnings")
    val warnings: List<Any>,
    @Json(name = "waypoint_order")
    val waypointOrder: List<Any>
)
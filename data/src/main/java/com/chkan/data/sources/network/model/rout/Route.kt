package com.chkan.data.sources.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Route(
    @SerialName("bounds")
    val bounds: Bounds,
    @SerialName("copyrights")
    val copyrights: String,
    @SerialName("legs")
    val legs: List<Leg>,
    @SerialName("overview_polyline")
    val overviewPolyline: OverviewPolyline,
    @SerialName("summary")
    val summary: String
  /*  @SerialName("warnings")
    val warnings: List<Any>,
    @SerialName("waypoint_order")
    val waypointOrder: List<Any>*/
)

//TODO: костыль - Any
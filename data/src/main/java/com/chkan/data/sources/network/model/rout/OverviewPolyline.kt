package com.chkan.data.sources.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OverviewPolyline(
    @SerialName("points")
    val points: String
)
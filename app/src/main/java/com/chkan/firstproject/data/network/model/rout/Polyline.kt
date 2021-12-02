package com.chkan.firstproject.data.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Polyline(
    @SerialName("points")
    val points: String
)
package com.chkan.data.sources.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartLocationX(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lng")
    val lng: Double
)
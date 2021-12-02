package com.chkan.firstproject.data.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EndLocationX(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lng")
    val lng: Double
)
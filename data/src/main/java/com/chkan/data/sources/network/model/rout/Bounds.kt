package com.chkan.data.sources.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bounds(
    @SerialName("northeast")
    val northeast: Northeast,
    @SerialName("southwest")
    val southwest: Southwest
)
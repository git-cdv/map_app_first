package com.chkan.data.sources.network.model.geocode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("formatted_address")
    val formattedAddress: String
)
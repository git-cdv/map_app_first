package com.chkan.firstproject.data.network.model.geocode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("formatted_address")
    val formattedAddress: String
)
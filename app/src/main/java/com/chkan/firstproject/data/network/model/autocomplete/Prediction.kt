package com.chkan.firstproject.data.network.model.autocomplete


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Prediction(
    @SerialName("description")
    val name: String,
    @SerialName("place_id")
    val placeId: String
)
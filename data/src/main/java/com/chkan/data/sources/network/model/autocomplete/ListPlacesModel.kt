package com.chkan.data.sources.network.model.autocomplete


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListPlacesModel(
    @SerialName("predictions")
    val listPlaces: List<Prediction>
)
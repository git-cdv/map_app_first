package com.chkan.firstproject.data.network.model.autocomplete


import com.google.gson.annotations.SerializedName

data class ListPlacesModel(
    @SerializedName("predictions")
    val listPlaces: List<Prediction>
)
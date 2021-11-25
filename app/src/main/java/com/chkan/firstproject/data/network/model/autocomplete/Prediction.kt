package com.chkan.firstproject.data.network.model.autocomplete


import com.google.gson.annotations.SerializedName

data class Prediction(
    @SerializedName("description")
    val name: String,
    @SerializedName("place_id")
    val placeId: String
)
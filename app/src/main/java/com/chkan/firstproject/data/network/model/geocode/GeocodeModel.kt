package com.chkan.firstproject.data.network.model.geocode


import com.google.gson.annotations.SerializedName

data class GeocodeModel(
    @SerializedName("results")
    val results: List<Result>
)
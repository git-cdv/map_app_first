package com.chkan.firstproject.data.network.model.geocode


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("formatted_address")
    val formattedAddress: String
)
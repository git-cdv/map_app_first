package com.chkan.firstproject.data.network.model.detail


import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("location")
    val location: Location
)
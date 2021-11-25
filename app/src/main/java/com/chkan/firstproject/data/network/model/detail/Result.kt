package com.chkan.firstproject.data.network.model.detail


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("geometry")
    val geometry: Geometry
)
package com.chkan.firstproject.data.network.model.detail


import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Double,
    val lng: Double
)
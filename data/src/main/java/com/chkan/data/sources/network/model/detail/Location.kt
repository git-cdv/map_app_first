package com.chkan.data.sources.network.model.detail


import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Double,
    val lng: Double
)
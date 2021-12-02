package com.chkan.firstproject.data.network.model.detail


import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val location: Location
)
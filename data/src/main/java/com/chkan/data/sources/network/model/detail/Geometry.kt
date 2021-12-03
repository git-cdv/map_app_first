package com.chkan.data.sources.network.model.detail

import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val location: Location
)
package com.chkan.data.sources.network.model.geocode


import kotlinx.serialization.Serializable

@Serializable
data class GeocodeModel(
    val results: List<Result>
)
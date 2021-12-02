package com.chkan.firstproject.data.network.model.geocode


import kotlinx.serialization.Serializable

@Serializable
data class GeocodeModel(
    val results: List<Result>
)
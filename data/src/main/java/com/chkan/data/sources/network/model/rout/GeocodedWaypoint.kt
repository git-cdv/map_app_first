package com.chkan.data.sources.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodedWaypoint(
    @SerialName("geocoder_status")
    val geocoderStatus: String,
    @SerialName("place_id")
    val placeId: String,
    @SerialName("types")
    val types: List<String>
)
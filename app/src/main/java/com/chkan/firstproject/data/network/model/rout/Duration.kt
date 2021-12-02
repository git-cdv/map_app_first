package com.chkan.firstproject.data.network.model.rout


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Duration(
    @SerialName("text")
    val text: String,
    @SerialName("value")
    val value: Int
)
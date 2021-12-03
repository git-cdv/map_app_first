package com.chkan.domain.models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultModel(
    val startName: String,
    val startLatNng: String,
    val finishName: String,
    val finishLatNng: String) :
    Parcelable

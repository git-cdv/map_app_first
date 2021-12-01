package com.chkan.firstproject.features.from.usecase

import com.chkan.firstproject.data.cash.CashDataSource
import com.chkan.firstproject.data.datatype.ResultType
import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.data.network.NetworkDataSource
import com.google.android.gms.maps.model.LatLng

class GetLatLngSelectedPlaceUseCase {

    private val networkDataSource = NetworkDataSource()

    suspend fun getLatLngSelectedPlace(name: String?): Result<LatLng> {
        val idSelected = CashDataSource.places.find { it.name == name }?.placeId
        val placeModel = idSelected?.let { networkDataSource.getDetailPlace(it) }
            return if (placeModel?.resultType == ResultType.SUCCESS && placeModel.data !=null) {
                    val lat = placeModel.data.result.geometry.location.lat
                    val lng = placeModel.data.result.geometry.location.lng
                    val latLng = LatLng(lat, lng)
                Result.success(latLng)
            } else {
                Result.error(placeModel?.error)
            }
    }
}
package com.chkan.firstproject.features.from.usecase

import com.chkan.firstproject.data.cash.CashDataSource
import com.chkan.firstproject.data.datatype.ResultType
import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.data.network.NetworkDataSource
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetLatLngSelectedPlaceUseCase @Inject constructor(private val networkDataSource : NetworkDataSource, private val cashDataSource : CashDataSource ) {

    suspend fun getLatLngSelectedPlace(name: String?): Result<LatLng> {
        val idSelected = cashDataSource.places.find { it.name == name }?.placeId
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
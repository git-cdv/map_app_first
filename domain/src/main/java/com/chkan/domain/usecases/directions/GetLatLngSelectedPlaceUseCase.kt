package com.chkan.domain.usecases.directions


import com.chkan.data.sources.cash.CashDataSource
import com.chkan.base.utils.ResultType
import com.chkan.base.utils.Result
import com.chkan.data.sources.network.NetworkDataSource
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetLatLngSelectedPlaceUseCase @Inject constructor(private val networkDataSource : NetworkDataSource, private val cashDataSource : CashDataSource) {

    suspend fun getLatLngSelectedPlace(name: String?): Result<LatLng> {
        val idSelected = cashDataSource.places.find { it.name == name }?.placeId
        val placeModel = idSelected?.let { networkDataSource.getDetailPlace(it) }
            return if (placeModel?.resultType == ResultType.SUCCESS && placeModel.data !=null) {
                    val lat = placeModel.data!!.result.geometry.location.lat
                    val lng = placeModel.data!!.result.geometry.location.lng
                    val latLng = LatLng(lat, lng)
                Result.success(latLng)
            } else {
                Result.error(placeModel?.error)
            }
    }
}
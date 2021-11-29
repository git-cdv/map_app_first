package com.chkan.firstproject.features.resultMap.usecase

import android.graphics.Color
import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.data.datatype.ResultType
import com.chkan.firstproject.data.network.NetworkDataSource
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil

class GetPolylineForRouteUseCase {
    private val networkDataSource = NetworkDataSource()

    suspend fun getPolylineForRoute(latLngStart: String, latLngFinish: String): Result <PolylineOptions> {

        val routAsGson = networkDataSource.getDirection(latLngStart,latLngFinish)

        return if (routAsGson.resultType== ResultType.SUCCESS){
            val shape = routAsGson.data?.routes?.get(0)?.overviewPolyline?.points
            val polyline = PolylineOptions()
                .addAll(PolyUtil.decode(shape))
                .width(8f)
                .color(Color.BLACK)

            Result.success(polyline)
        } else {
            Result.error(routAsGson.error)
        }


    }



}
package com.chkan.domain.usecases.result_map

import android.graphics.Color
import com.chkan.base.utils.ResultType
import com.chkan.base.utils.Result
import com.chkan.data.sources.network.NetworkDataSource
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import javax.inject.Inject

class GetPolylineForRouteUseCase @Inject constructor(private val networkDataSource : NetworkDataSource) {

    suspend fun getPolylineForRoute(latLngStart: String, latLngFinish: String): Result<PolylineOptions> {

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
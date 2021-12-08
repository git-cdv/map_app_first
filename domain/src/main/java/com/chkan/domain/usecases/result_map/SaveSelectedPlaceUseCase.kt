package com.chkan.domain.usecases.result_map

import com.chkan.base.utils.*
import com.chkan.domain.models.ResultModel
import com.chkan.data.sources.local.LocalDataSource
import com.chkan.data.sources.network.NetworkDataSource
import com.chkan.domain.models.LocalModelUI
import com.chkan.domain.models.asLocalModelUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveSelectedPlaceUseCase @Inject constructor(private val networkDataSource : NetworkDataSource, private val localDataSource : LocalDataSource) {

    fun savePlace(resultModel : ResultModel) {
        GlobalScope.launch (Dispatchers.IO) {
            var nameStart = resultModel.startName
            var nameFinish = resultModel.finishName
            val latlngStart = resultModel.startLatNng
            val latlngFinish = resultModel.finishLatNng

            if(nameStart.isEmpty()){//сходить в геокодинг за именем
                val result = networkDataSource.getNameFromGeocoding(latlngStart)
                nameStart = if(result.resultType== ResultType.SUCCESS && !result.data?.results.isNullOrEmpty() ){
                    result.data?.results?.get(0)?.formattedAddress ?: "Unknown"
                } else {
                    "Unknown"
                }
            }
            if(nameFinish.isEmpty()){//сходить в геокодинг за именем
                val result = networkDataSource.getNameFromGeocoding(latlngFinish)
                nameFinish = if(result.resultType== ResultType.SUCCESS && !result.data?.results.isNullOrEmpty()){
                    result.data?.results?.get(0)?.formattedAddress ?: "Unknown"
                } else {
                    "Unknown"
                }
            }
            localDataSource.saveAsStart(nameStart,latlngStart)
            localDataSource.saveAsFinish(nameFinish,latlngFinish)
        }
    }

    suspend fun getFromHistory(type: String) : List<LocalModelUI> {
        return localDataSource.getListHistory(type).asLocalModelUI()
    }
}
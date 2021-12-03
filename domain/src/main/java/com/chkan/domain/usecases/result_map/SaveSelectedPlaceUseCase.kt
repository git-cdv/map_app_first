package com.chkan.domain.usecases.result_map

import com.chkan.base.utils.ResultType
import com.chkan.data.sources.local.LocalModel
import com.chkan.domain.models.ResultModel
import com.chkan.base.utils.Constans
import com.chkan.base.utils.toLatLng
import com.chkan.base.utils.toStringModel
import com.chkan.data.sources.local.LocalDataSource
import com.chkan.data.sources.network.NetworkDataSource
import com.chkan.domain.models.LocalModelUI
import com.chkan.domain.models.asLocalModelUI
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SaveSelectedPlaceUseCase @Inject constructor(private val networkDataSource : NetworkDataSource, private val localDataSource : LocalDataSource) {

    fun savePlace(resultModel : ResultModel) {
        GlobalScope.launch {
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
            saveInList(Constans.PREF_LIST_START,nameStart,latlngStart.toLatLng())
            saveInList(Constans.PREF_LIST_FINISH,nameFinish,latlngFinish.toLatLng())
        }
    }

    private fun saveInList(nameList: String, name: String?, latlng: LatLng?) {
        val dataOfString = localDataSource.getString(nameList)

        if (dataOfString.isNullOrEmpty()) {//если списка нет
            val listLocalModel = arrayListOf<LocalModel>()
            listLocalModel.add(LocalModel(name!!,latlng!!.toStringModel()))
            val modelListOfString = Json.encodeToString(listLocalModel)
            localDataSource.add(nameList,modelListOfString)
        } else{
            val listLocalModel = Json.decodeFromString<ArrayList<LocalModel>>(dataOfString)
            listLocalModel.add(LocalModel(name!!,latlng!!.toStringModel()))
            val modelListOfString = Json.encodeToString(listLocalModel)
            localDataSource.add(nameList,modelListOfString)
        }
    }

    fun getFromHistory(who: Int) : List<LocalModelUI> {
        return if(who==Constans.WHO_FROM){
            val modelListOfString = localDataSource.getString(Constans.PREF_LIST_START)
            getListLocalModel(modelListOfString).asLocalModelUI()
        } else {
            val modelListOfString = localDataSource.getString(Constans.PREF_LIST_FINISH)
            getListLocalModel(modelListOfString).asLocalModelUI()
        }
    }

    private fun getListLocalModel(model: String?): ArrayList<LocalModel> {
        return if (model != null) {
            Json.decodeFromString(model)
        } else{
            arrayListOf(LocalModel("Пока нет сохранений",null))
        }
    }
}
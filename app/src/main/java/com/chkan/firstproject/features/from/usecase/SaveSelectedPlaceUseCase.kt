package com.chkan.firstproject.features.from.usecase

import android.util.Log
import com.chkan.firstproject.data.datatype.ResultType
import com.chkan.firstproject.data.local.LocalModel
import com.chkan.firstproject.data.network.NetworkDataSource
import com.chkan.firstproject.features.result_map.model.ResultModel
import com.chkan.firstproject.utils.Constans
import com.chkan.firstproject.utils.MyApp
import com.chkan.firstproject.utils.toLatLng
import com.chkan.firstproject.utils.toStringModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SaveSelectedPlaceUseCase @Inject constructor(private val networkDataSource : NetworkDataSource) {

    private val localDataSource = MyApp.localData
    private val applicationScopeIO = MyApp.instance.applicationScopeIO

    fun savePlace(resultModel : ResultModel) {
        applicationScopeIO.launch {
            var nameStart = resultModel.startName
            var nameFinish = resultModel.finishName
            val latlngStart = resultModel.startLatNng
            val latlngFinish = resultModel.finishLatNng

            if(nameStart.isEmpty()){//сходить в геокодинг за именем
                val result = networkDataSource.getNameFromGeocoding(latlngStart)
                nameStart = if(result.resultType== ResultType.SUCCESS){
                    result.data?.results?.get(0)?.formattedAddress ?: "Unknown"
                } else {
                    "Unknown"
                }
            }
            if(nameFinish.isEmpty()){//сходить в геокодинг за именем
                val result = networkDataSource.getNameFromGeocoding(latlngFinish)
                nameFinish = if(result.resultType== ResultType.SUCCESS){
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
            Log.d("MYAPP", "savePlaceFromSearch - modelListOfModel: $modelListOfString")
        }
    }

    fun getFromHistory(who: Int) : ArrayList<LocalModel> {
        return if(who==Constans.WHO_FROM){
            val modelListOfString = localDataSource.getString(Constans.PREF_LIST_START)
            getListLocalModel(modelListOfString)
        } else {
            val modelListOfString = localDataSource.getString(Constans.PREF_LIST_FINISH)
            getListLocalModel(modelListOfString)
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
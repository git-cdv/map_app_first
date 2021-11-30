package com.chkan.firstproject.features.from.usecase

import android.util.Log
import com.chkan.firstproject.data.datatype.ResultType
import com.chkan.firstproject.data.local.LocalModel
import com.chkan.firstproject.data.network.NetworkDataSource
import com.chkan.firstproject.utils.Constans
import com.chkan.firstproject.utils.MyApp
import com.chkan.firstproject.utils.toLatLng
import com.chkan.firstproject.utils.toStringModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SaveSelectedPlaceUseCase {

    private val localDataSource = MyApp.localData
    private val applicationScopeIO = MyApp.instance.applicationScopeIO
    private val networkDataSource = NetworkDataSource()

    fun savePlace(nameOfStart: String, latLngStart: String, nameOfFinish: String, latLngFinish: String) {
        applicationScopeIO.launch {
            var nameStart = nameOfStart
            var nameFinish = nameOfFinish
            if(nameStart.isEmpty()){//сходить в геокодинг за именем
                val result = networkDataSource.getNameFromGeocoding(latLngStart)
                nameStart = if(result.resultType== ResultType.SUCCESS){
                    result.data?.results?.get(0)?.formattedAddress ?: "Unknown"
                } else {
                    "Unknown"
                }
            }
            if(nameOfFinish.isEmpty()){//сходить в геокодинг за именем
                val result = networkDataSource.getNameFromGeocoding(latLngFinish)
                nameFinish = if(result.resultType== ResultType.SUCCESS){
                    result.data?.results?.get(0)?.formattedAddress ?: "Unknown"
                } else {
                    "Unknown"
                }
            }
            saveInList(Constans.PREF_LIST_START,nameStart,latLngStart.toLatLng())
            saveInList(Constans.PREF_LIST_FINISH,nameFinish,latLngFinish.toLatLng())
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

}
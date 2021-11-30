package com.chkan.firstproject.features.from.usecase

import android.util.Log
import com.chkan.firstproject.data.local.LocalModel
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

    fun savePlace(nameStart: String, latLngStart: String, nameFinish: String, latLngFinish: String) {
        applicationScopeIO.launch {
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
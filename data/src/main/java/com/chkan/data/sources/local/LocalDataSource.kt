package com.chkan.data.sources.local

import com.chkan.base.utils.DB_TYPE_FINISH
import com.chkan.base.utils.DB_TYPE_START
import javax.inject.Inject

class LocalDataSource @Inject constructor (private val historyDao : HistoryDao){

    fun saveAsStart(nameStart: String, latlngStart: String) {
        historyDao.insert(DatabaseModel(name=nameStart, latlng = latlngStart, type = DB_TYPE_START))
    }

    fun saveAsFinish(nameFinish: String, latlngFinish: String) {
        historyDao.insert(DatabaseModel(name=nameFinish, latlng = latlngFinish, type = DB_TYPE_FINISH))
    }

    suspend fun getListHistory(type: String): List<DatabaseModel> {
        return historyDao.getListHistory(type)
    }

}
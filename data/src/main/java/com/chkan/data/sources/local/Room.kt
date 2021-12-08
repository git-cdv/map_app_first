package com.chkan.data.sources.local

import androidx.room.*

@Dao
interface HistoryDao {

    @Query("select * from databasemodel WHERE type = :type")
    suspend fun getListHistory(type:String): List<DatabaseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( item: DatabaseModel)
}

@Database(entities = [DatabaseModel::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract val historyDao: HistoryDao
}

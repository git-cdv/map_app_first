package com.chkan.data.sources.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "latlng")
    val latlng: String,
    @ColumnInfo(name = "type")
    val type: String
)
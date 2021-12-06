package com.chkan.data.sources.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import javax.inject.Inject

class LocalDataSource @Inject constructor (context: Context){

    val pref: SharedPreferences = context.getSharedPreferences("app_pref",MODE_PRIVATE)

    fun add (key:String, value: String){
        pref.edit().putString(key,value).apply()
    }

    fun getString (key : String): String? {
        return pref.getString(key,null)
    }
}
package com.chkan.firstproject.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class LocalDataSource (context: Context){

    val pref: SharedPreferences = context.getSharedPreferences("app_pref",MODE_PRIVATE)

    fun add (key:String, value: Boolean){
        pref.edit().putBoolean(key,value).apply()
    }

    fun add (key:String, value: String){
        pref.edit().putString(key,value).apply()
    }

    fun add (key:String, value: Int){
        pref.edit().putInt(key,value).apply()
    }

    fun getString (key : String): String? {
        return pref.getString(key,null)
    }
}
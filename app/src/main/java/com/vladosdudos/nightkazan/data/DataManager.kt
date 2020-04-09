package com.vladosdudos.nightkazan.data

import android.content.Context
import android.content.SharedPreferences

class DataManager{
    private val pref: SharedPreferences
    private val baseContext: Context

    val api = Api.createApi()

    constructor(baseContext: Context){
        this.baseContext = baseContext
        pref = baseContext.getSharedPreferences("NightKazan", Context.MODE_PRIVATE)
    }

    fun isFirstLaunch(): Boolean = pref.getBoolean("isFirstLaunch", true)

    fun endFirstLaunch() : Boolean = pref.edit().putBoolean("isFirstLaunch", false).commit()
}
package com.vladosdudos.nightkazan.app

import android.app.Application
import com.vladosdudos.nightkazan.data.DataManager

class App :Application(){

    companion object{
        lateinit var dm: DataManager
    }
    override fun onCreate() {
        super.onCreate()
        dm = DataManager(baseContext)
    }
}
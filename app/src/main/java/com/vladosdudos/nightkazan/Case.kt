package com.vladosdudos.nightkazan

import android.content.Context
import com.google.gson.Gson

object Case {

    fun saveInfo(context: Context, info: Info) {
        val pref = context.getSharedPreferences("profile_info", Context.MODE_PRIVATE)

        val json = Gson().toJson(info)

        pref.edit().putString("info", json).apply()

    }

    fun getInfo(context: Context): Info {
        val shared = context.getSharedPreferences("profile_info", Context.MODE_PRIVATE)
        return Gson().fromJson<Info>(shared.getString("info", "{}"), Info::class.java)

    }
}
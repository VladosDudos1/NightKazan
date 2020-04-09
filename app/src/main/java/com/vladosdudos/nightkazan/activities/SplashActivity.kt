package com.vladosdudos.nightkazan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.vladosdudos.nightkazan.app.App

class SplashActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        if (App.dm.isFirstLaunch()) {
            startActivity(Intent(this, RegistrationActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}

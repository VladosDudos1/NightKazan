package com.vladosdudos.nightkazan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vladosdudos.nightkazan.R
import com.vladosdudos.nightkazan.app.App
import kotlinx.android.synthetic.main.activity_reg3.*

class Reg3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg3)

        cont3_btn.setOnClickListener {
            App.dm.endFirstLaunch()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}

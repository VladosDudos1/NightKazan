package com.vladosdudos.nightkazan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vladosdudos.nightkazan.R
import kotlinx.android.synthetic.main.activity_reg2.*

class Reg2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg2)

        cont2_btn.setOnClickListener {
            startActivity(Intent(this, Reg3Activity::class.java))
        }
    }
}

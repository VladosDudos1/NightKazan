package com.vladosdudos.nightkazan.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.vladosdudos.nightkazan.Case
import com.vladosdudos.nightkazan.R
import com.vladosdudos.nightkazan.Info
import com.vladosdudos.nightkazan.app.App
import com.vladosdudos.nightkazan.getPhotoFromUri
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class RegistrationActivity : AppCompatActivity() {

    val shared = lazy { this.getSharedPreferences("app", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        cont_btn.setOnClickListener {
            save()
            startActivity(Intent(this, Reg2Activity::class.java))
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED
        ) {


            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 31
            )
        }
        user_photo.setOnClickListener {
            selectImg()
        }

        try {
            val path = shared.value.getString("photo_path", "")
            if (!path.isNullOrEmpty())
                user_photo.setImageURI(path.toUri())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            31 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    return
            }
        }
    }
    fun selectImg() {
        MaterialDialog(this)
            .title(text = "Choose from ")
            .listItemsSingleChoice(
                items = listOf("Gallery", "Camera"),
                selection = { dialog, index, text ->
                    if (index == 0) gallery() else camera()
                    dialog.cancel()
                }).show {}
    }

    private fun camera() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1)
    }

    private fun gallery() {
        try {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        } catch (ex: Exception) {

        }
    }
    private var bitmap: Bitmap? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (data == null) return
        if (requestCode == 0) {
            data.data?.let {
                bitmap = getPhotoFromUri(this, it)
            }
        } else if (requestCode == 1) {
            bitmap = data.extras!!.get("data") as Bitmap
        }
        user_photo.setImageBitmap(bitmap)

        if (bitmap != null) {
            val path = getExternalStorageDirectory().toString()
            var fOut: OutputStream?
            val file = File(path, "Yuuechka.jpg")
            fOut = FileOutputStream(file)
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
            fOut.flush()
            fOut.close()
            shared.value.edit().putString("photo_path",file.absolutePath).apply()
        }

    }




    fun save() {
        val info = Info()
        info.apply {
            name = name_edit.text.toString()
            surname = surname_edit.text.toString()
            nick = nick_edit.text.toString()
            password = password_edit.text.toString()
            email = mail_edit.text.toString()

        }
        Case.saveInfo(this, info)


        if (mail_edit.text.toString().contains("@")) {
            if (name_edit.text.toString().isNotEmpty()) {
                if (password_edit.text.toString().isNotEmpty()) {
                    if (surname_edit.text.toString().isNotEmpty()) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(this, "Please, enter your surname", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else Toast.makeText(this, "Please, enter password", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please, enter your name", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Wrong Email", Toast.LENGTH_SHORT).show()
        }
    }


}


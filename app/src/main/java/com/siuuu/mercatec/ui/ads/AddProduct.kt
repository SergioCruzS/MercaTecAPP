package com.siuuu.mercatec.ui.ads

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.siuuu.mercatec.databinding.ActivityAddProductBinding
import java.io.File

class AddProduct : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    lateinit var archivoFoto: File
    var nombreFoto: String = "photo.jpg"
    val REQ_CODE_CAM: Int = 1
    val REQ_CODE_GAL: Int = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAddProduct)
        binding.ivBackToolbarAdd.setOnClickListener(){
            onBackPressed()
        }

        binding.ivDoneToolbarAdd.setOnClickListener(){
            Toast.makeText(this,"Agregar producto",Toast.LENGTH_SHORT).show()
        }

        binding.btPhotoAddProduct.setOnClickListener(){
            try {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                archivoFoto = getPhotoFile(nombreFoto)
                val fileProvider =
                    FileProvider.getUriForFile(this, "com.example.pruebacamara", archivoFoto)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
                startActivityForResult(intent, REQ_CODE_CAM)
                Toast.makeText(applicationContext, "Foto capturada", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Toast.makeText(applicationContext, "Error al abrir la cámara", Toast.LENGTH_SHORT).show()
            }


        }

    }

    private fun getPhotoFile(nombreFoto: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(nombreFoto, ".jpg", storageDirectory)
    }



}
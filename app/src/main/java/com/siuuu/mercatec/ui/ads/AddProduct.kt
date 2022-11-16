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
import com.siuuu.mercatec.ui.imageSlider.SliderAdapter
import com.siuuu.mercatec.ui.imageSlider.SliderAdapterAddProduct
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import java.io.File

class AddProduct : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    lateinit var archivoFoto: File
    var nombreFoto: String = "photo.jpg"
    val REQ_CODE_CAM: Int = 1
    val REQ_CODE_GAL: Int = 2
    lateinit var sliderView: SliderView
    lateinit var adapter: SliderAdapterAddProduct
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAddProduct)

        sliderView = this.binding.imageSliderAddProduct
        adapter = SliderAdapterAddProduct(this)
        sliderView.setSliderAdapter(adapter)
        sliderView.scrollTimeInSec = 2
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)

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
                val fileProvider = FileProvider.getUriForFile(this, "com.siuuu.mercatec", archivoFoto)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
                startActivityForResult(intent, REQ_CODE_CAM)
                Toast.makeText(applicationContext, "Abriendo cámara", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Toast.makeText(applicationContext, "Error al abrir la cámara", Toast.LENGTH_SHORT).show()
            }


        }

    }

    private fun getPhotoFile(nombreFoto: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(nombreFoto, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            try {
                //imgVwFoto.setImageBitmap(data?.extras?.get("data") as Bitmap)
                adapter.addItem(archivoFoto)
                Toast.makeText(applicationContext, "Foto capturada", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Toast.makeText(applicationContext, "Error al capturar foto", Toast.LENGTH_SHORT).show()
            }
        }
    }



}
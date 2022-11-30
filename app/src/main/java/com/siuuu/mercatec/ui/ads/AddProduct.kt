package com.siuuu.mercatec.ui.ads

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siuuu.mercatec.MainActivity
import com.siuuu.mercatec.databinding.ActivityAddProductBinding
import com.siuuu.mercatec.ui.imageSlider.SliderAdapterAddProduct
import com.siuuu.mercatec.ui.login.preference
import com.siuuu.mercatec.ui.values.ImageEncodeAndDecode
import com.siuuu.mercatec.ui.values.Strings
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import org.json.JSONObject
import java.io.File

class AddProduct : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    lateinit var archivoFoto: File
    var nombreFoto: String = "mercatec_img_"
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
            val queue = Volley.newRequestQueue(this)
            val url = Strings.url_base + Strings.url_post_ad
            val json = JsonObjectRequest(
                Request.Method.POST, url,
                JSONObject(
                    AdToJSON(
                        preference.preferenceManager(this).getString("uid","null").toString(),
                        preference.preferenceManager(this).getString("name","nombre").toString(),
                        preference.preferenceManager(this).getString("phone","9999999999").toString(),
                    binding.tvTitleDetail.text.toString(),
                    binding.tvPriceAndAvailableDetail.text.toString(),
                    binding.tvDescriptionDetail.text.toString(),
                    adapter.itemsEnc).toJson()),
                Response.Listener { response ->
                    //println("resp: "+response.toString())
                    Toast.makeText(this, "Envío correcto", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    this?.startActivity(intent)
                    finish()
                },
                Response.ErrorListener { error -> Toast.makeText(this,"$error", Toast.LENGTH_SHORT).show()}

            )
            json.retryPolicy = DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(json)
            Toast.makeText(this,"Agregado con éxito",Toast.LENGTH_SHORT).show()
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
                var imageEncode = ImageEncodeAndDecode.encode(archivoFoto)
                adapter.addItem(ImageEncodeAndDecode.decode(imageEncode,getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!))
                Toast.makeText(applicationContext, "Foto capturada", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Toast.makeText(applicationContext, "Error al capturar foto", Toast.LENGTH_SHORT).show()
            }
        }
    }



}
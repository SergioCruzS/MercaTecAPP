package com.siuuu.mercatec.ui.ads

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.Continuation
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.siuuu.mercatec.MainActivity
import com.siuuu.mercatec.databinding.ActivityAddProductBinding
import com.siuuu.mercatec.ui.imageSlider.SliderAdapterAddProduct
import com.siuuu.mercatec.ui.login.preference
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
    //val REQ_CODE_GAL: Int = 2
    lateinit var sliderView: SliderView
    lateinit var adapter: SliderAdapterAddProduct
    lateinit var mStorage:StorageReference
    //var listUrl:ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAddProduct)

        mStorage = FirebaseStorage.getInstance().getReference()

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
            if (adapter.urls.isEmpty()){
                adapter.addUrls("https://st4.depositphotos.com/14953852/22772/v/600/depositphotos_227725020-stock-illustration-no-image-available-icon-flat.jpg")
            }

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
                        adapter.urls).toJson()),
                Response.Listener { response ->
                    //println("resp: "+response.toString())
                    val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    this.startActivity(intent)
                    finish()
                },
                Response.ErrorListener { error -> Toast.makeText(this,"Verifique que llenó todos los campos", Toast.LENGTH_SHORT).show()}

            )
            queue.add(json)
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
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                //var imageEncode = ImageEncodeAndDecode.encode(archivoFoto)
                var uri:Uri = archivoFoto.toUri()
                var filePath = mStorage.child("fotos").child(uri.lastPathSegment!!)
                var task = filePath.putFile(uri).addOnSuccessListener {
                    Toast.makeText(applicationContext, "Foto capturada", Toast.LENGTH_SHORT).show()
                }
                var urlTask = task.continueWithTask(Continuation { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    filePath.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val dowloadUri = task.result
                        adapter.addUrls(dowloadUri.toString())
                        println(dowloadUri.toString())
                    }
                }
                adapter.addItem(archivoFoto)
            }catch (e: Exception){
                Toast.makeText(applicationContext, "Error al capturar foto", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun sendDb(listUrl:ArrayList<String>,context: Context){

    }



}
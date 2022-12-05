package com.siuuu.mercatec.ui.detalle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.slider.Slider
import com.siuuu.mercatec.MainActivity
import com.siuuu.mercatec.databinding.ActivityProductDetailBinding
import com.siuuu.mercatec.ui.ads.DeleteAd
import com.siuuu.mercatec.ui.chat.ChatActivity
import com.siuuu.mercatec.ui.home.Product
import com.siuuu.mercatec.ui.imageSlider.SliderAdapter
import com.siuuu.mercatec.ui.imageSlider.SliderAdapterAddProduct
import com.siuuu.mercatec.ui.imageSlider.SliderAdapterUrl
import com.siuuu.mercatec.ui.login.UpdateJSON
import com.siuuu.mercatec.ui.login.preference
import com.siuuu.mercatec.ui.values.Strings
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import org.json.JSONObject
import java.io.File

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarDetail)
        //ImageSlider

        var sliderView:SliderView = this.binding.imageSliderDetail
        var adapter = SliderAdapterUrl(this)
        var bundle = intent.extras
        var listImages :Product = bundle?.getSerializable("data") as Product

        for (i in listImages.image?.indices!!){
            adapter.addItem(listImages.image!!.get(i))
        }
        if (bundle.getBoolean("contact")){
            binding.btSendMessageProductDetail.visibility = View.VISIBLE
            binding.btEliminarAnuncio.visibility = View.GONE
        }else{
            binding.btSendMessageProductDetail.visibility = View.GONE
            binding.btEliminarAnuncio.visibility = View.VISIBLE
        }
        binding.tvTitleDetail.text = listImages.name
        binding.tvPriceAndAvailableDetail.text = listImages.price.toString() +" mxn"
        binding.tvDescriptionDetail.text = listImages.description
        sliderView.setSliderAdapter(adapter)
        sliderView.scrollTimeInSec = 2
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)

        binding.ivBackToolbarDetail.setOnClickListener(){
            this.onBackPressed()
        }

        binding.btEliminarAnuncio.setOnClickListener(){
            val queue = Volley.newRequestQueue(this)
            val url = Strings.url_base + Strings.url_post_deleteAd
            val json = JsonObjectRequest(
                Request.Method.POST, url,
                JSONObject(
                    DeleteAd(
                    preference.preferenceManager(this).getString("uid", "null").toString(),
                    binding.tvTitleDetail.text.toString()
                ).toJson()),
                Response.Listener { response ->
                    //println("resp: "+response.toString())
                    Toast.makeText(this, "Eliminado Correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    this?.startActivity(intent)
                    finish()
                },
                Response.ErrorListener { error -> Toast.makeText(this,"$error", Toast.LENGTH_SHORT).show() }

            )
            queue.add(json)
        }

        binding.btSendMessageProductDetail.setOnClickListener(){
            var url = "https://wa.me/+52"+ listImages.phone.toString()
            var uri: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW,uri)
            this?.startActivity(intent)
        }




    }

}
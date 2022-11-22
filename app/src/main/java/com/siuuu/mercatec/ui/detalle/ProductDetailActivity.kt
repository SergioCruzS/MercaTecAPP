package com.siuuu.mercatec.ui.detalle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.slider.Slider
import com.siuuu.mercatec.databinding.ActivityProductDetailBinding
import com.siuuu.mercatec.ui.chat.ChatActivity
import com.siuuu.mercatec.ui.home.Product
import com.siuuu.mercatec.ui.imageSlider.SliderAdapter
import com.siuuu.mercatec.ui.imageSlider.SliderAdapterAddProduct
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import java.io.File

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarDetail)


        binding.ivBackToolbarDetail.setOnClickListener(){
            this.onBackPressed()
        }

        binding.btSendMessageProductDetail.setOnClickListener(){
            val intent = Intent(this,ChatActivity::class.java)
            this?.startActivity(intent)
        }

        //ImageSlider

        var sliderView:SliderView = this.binding.imageSliderDetail
        var adapter = SliderAdapterAddProduct(this)
        var bundle = intent.extras
        var listImages :Product = bundle?.getSerializable("data") as Product
        for (i in listImages.image?.indices!!){
            adapter.addItem(listImages.image!!.get(i))
        }
        if (bundle.getBoolean("contact")){
            binding.btSendMessageProductDetail.visibility = View.VISIBLE
        }else{
            binding.btSendMessageProductDetail.visibility = View.GONE
        }
        binding.tvTitleDetail.text = listImages.name
        binding.tvPriceAndAvailableDetail.text = listImages.price.toString()
        binding.tvDescriptionDetail.text = listImages.description
        sliderView.setSliderAdapter(adapter)
        sliderView.scrollTimeInSec = 2
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)

    }

}
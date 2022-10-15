package com.siuuu.mercatec.ui.detalle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.slider.Slider
import com.siuuu.mercatec.databinding.ActivityProductDetailBinding
import com.siuuu.mercatec.ui.chat.ChatActivity
import com.siuuu.mercatec.ui.imageSlider.SliderAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

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
        var adapter:SliderAdapter = SliderAdapter(this)
        adapter.addItem("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg")
        adapter.addItem("https://cdn.pixabay.com/photo/2022/07/02/17/24/abstract-7297671__480.jpg")
        sliderView.setSliderAdapter(adapter)
        sliderView.scrollTimeInSec = 2
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)

    }

}
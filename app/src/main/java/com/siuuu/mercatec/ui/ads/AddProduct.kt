package com.siuuu.mercatec.ui.ads

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.siuuu.mercatec.databinding.ActivityAddProductBinding

class AddProduct : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
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
        }

    }



}
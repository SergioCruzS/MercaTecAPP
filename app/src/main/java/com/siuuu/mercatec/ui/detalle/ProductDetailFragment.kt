package com.siuuu.mercatec.ui.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.siuuu.mercatec.R
import com.siuuu.mercatec.databinding.FragmentProductDetailBinding
import com.siuuu.mercatec.ui.home.HomeFragment

class ProductDetailFragment(nombre:String) : Fragment(){

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val nombreDetalle = nombre
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        //val root:View = binding.root
        var buttonBack: Button? = binding.btBackHome
        var text: TextView = binding.textView2
        text.setText(nombreDetalle)
        buttonBack?.setOnClickListener {
            println("listener")
            //Fragment Transactions
            val fragmentManager = activity?.supportFragmentManager
            // Create and commit a new transaction
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_content_main,HomeFragment())
            transaction?.commit()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
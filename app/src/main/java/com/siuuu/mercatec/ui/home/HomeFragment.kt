package com.siuuu.mercatec.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siuuu.mercatec.databinding.FragmentHomeBinding
import com.siuuu.mercatec.ui.chat.ChatActivity
import com.siuuu.mercatec.ui.detalle.ProductDetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    var listProducts:RecyclerView? = null
    var layoutManager:RecyclerView.LayoutManager? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val productos = ArrayList<Product>()
        productos.add(Product("Sergio", "Arduino UNO", "Arduino UNO usado en perfectas condiciones",399.00,4.0,com.siuuu.mercatec.R.drawable.arduino_uno))
        productos.add(Product("Kenia", "Resistencias", "Resistencias de 180 y 220 ohms nuevas",1.50,4.5,com.siuuu.mercatec.R.drawable.resistencia_180))
        productos.add(Product("Susana", "Multímetro", "Marca truper, excelentes condiciones ",230.00,5.0,com.siuuu.mercatec.R.drawable.multimetro_truper))
        productos.add(Product("Samuel", "Resistencias", "Resistencias de 220 ohms nuevas",2.00,4.5,com.siuuu.mercatec.R.drawable.resistencia_220))
        productos.add(Product("Yadira", "Multímetro", "Marca SEAFON, excelentes condiciones ",100.00,3.7,com.siuuu.mercatec.R.drawable.multimetro_seafon))

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        listProducts = binding.rvProducts
        listProducts?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.context)
        listProducts?.layoutManager = layoutManager
        listProducts?.adapter = AdaptadorCustom(productos, object:ClickListener{
            override fun onClick(vista: View, index: Int) {
                Toast.makeText(requireContext(), productos[index].name, Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), ProductDetailActivity::class.java)
                requireContext()?.startActivity(intent)
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


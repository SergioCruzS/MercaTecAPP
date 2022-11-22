package com.siuuu.mercatec.ui.ads

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siuuu.mercatec.databinding.FragmentAdsBinding
import com.siuuu.mercatec.ui.detalle.ProductDetailActivity
import com.siuuu.mercatec.ui.home.ClickListener
import com.siuuu.mercatec.ui.home.Product
import com.siuuu.mercatec.ui.values.ImageEncodeAndDecode
import com.siuuu.mercatec.ui.values.Strings
import org.json.JSONObject
import java.io.File

class AdsFragment : Fragment() {
    private var _binding : FragmentAdsBinding? = null
    private val binding get() = _binding!!
    var listProducts: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val queue = Volley.newRequestQueue(requireContext())
        val url = Strings.url_get_ad
        val json = object: JsonObjectRequest(
            Request.Method.GET, url,
            JSONObject(),
            Response.Listener { response ->
                //println("resp: "+response.toString())
                addListAds(response)
                Toast.makeText(context, "Consulta correcta", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error -> Toast.makeText(context,"$error", Toast.LENGTH_SHORT).show()}

        ){

        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String>? {
            val params: MutableMap<String, String> =
                HashMap()
            params["uid"] = "111"
            return params
        }}
        queue.add(json)


        //productos.add(Product("Sergio", "Arduino UNO", "Arduino UNO usado en perfectas condiciones",399.00,4.0,com.siuuu.mercatec.R.drawable.arduino_uno))
        //productos.add(Product("Kenia", "Resistencias", "Resistencias de 180 y 220 ohms nuevas",1.50,4.5,com.siuuu.mercatec.R.drawable.resistencia_180))
        //productos.add(Product("Susana", "Multímetro", "Marca truper, excelentes condiciones ",230.00,5.0,com.siuuu.mercatec.R.drawable.multimetro_truper))
        //productos.add(Product("Samuel", "Resistencias", "Resistencias de 220 ohms nuevas",2.00,4.5,com.siuuu.mercatec.R.drawable.resistencia_220))
        //productos.add(Product("Yadira", "Multímetro", "Marca SEAFON, excelentes condiciones ",100.00,3.7,com.siuuu.mercatec.R.drawable.multimetro_seafon))



        binding.btAddAds.setOnClickListener(){
            val intent = Intent(context,AddProduct::class.java)
            this?.startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun addListAds(response: JSONObject){
        val productos = ArrayList<Product>()
        var jsonOb = AdResponse.jsonAd(response.toString())
        for (i in jsonOb?.ads?.indices!!){
            var arrFilesImages = ArrayList<File>()
            for (j in jsonOb?.ads?.get(i)?.img?.indices!!){
                arrFilesImages.add(ImageEncodeAndDecode.decode(jsonOb?.ads?.get(i)?.img!!.get(j),context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!))
            }
            productos.add(Product(jsonOb?.ads?.get(i)?.uid.toString(),
                jsonOb?.ads?.get(i)?.title.toString(),
                jsonOb?.ads?.get(i)?.description.toString(),
                jsonOb?.ads?.get(i)?.price!!.toDouble(),
                0.0,
                arrFilesImages))
        }

        listProducts = binding.rvAds
        listProducts?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.context)
        listProducts?.layoutManager = layoutManager
        listProducts?.adapter = CustomAdapterAds(productos, object: ClickListener {
            override fun onClick(vista: View, index: Int) {
                Toast.makeText(requireContext(), productos[index].name, Toast.LENGTH_SHORT).show()
                var extras = Bundle()
                extras.putSerializable("data",productos[index])
                extras.putBoolean("contact",false)

                val intent = Intent(requireContext(), ProductDetailActivity::class.java)
                intent.putExtras(extras)
                requireContext()?.startActivity(intent)
            }
        })
    }

}
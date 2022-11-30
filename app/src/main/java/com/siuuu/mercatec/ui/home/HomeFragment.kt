package com.siuuu.mercatec.ui.home

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
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siuuu.mercatec.databinding.FragmentHomeBinding
import com.siuuu.mercatec.ui.ads.AdResponse
import com.siuuu.mercatec.ui.detalle.ProductDetailActivity
import com.siuuu.mercatec.ui.login.preference
import com.siuuu.mercatec.ui.values.ImageEncodeAndDecode
import com.siuuu.mercatec.ui.values.Strings
import org.json.JSONObject
import java.io.File


class HomeFragment : Fragment() {

    var listProducts:RecyclerView? = null
    var layoutManager:RecyclerView.LayoutManager? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.ivLoadingHome.visibility = View.VISIBLE
        binding.rvProducts.visibility = View.GONE
        val root: View = binding.root
        val url = Strings.url_base + Strings.url_get_adsHome
        val queue = Volley.newRequestQueue(context)
        val json = JsonObjectRequest(
            Request.Method.GET, url,
            JSONObject(),
            Response.Listener { response ->
                //println("resp: "+response.toString())
                addListAds(response)
                binding.ivLoadingHome.visibility = View.GONE
                binding.rvProducts.visibility = View.VISIBLE
            },
            Response.ErrorListener { error -> Toast.makeText(context,"$error", Toast.LENGTH_SHORT).show()}

        )
        json.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(json)


        return root
    }

    private fun addListAds(response: JSONObject){
        val productos = ArrayList<Product>()
        var jsonOb = AdResponse.jsonAd(response.toString())
        for (i in jsonOb?.ads?.indices!!){
            var arrFilesImages = ArrayList<File>()
            for (j in jsonOb?.ads?.get(i)?.img?.indices!!){
                arrFilesImages.add(
                    ImageEncodeAndDecode.decode(jsonOb?.ads?.get(i)?.img!!.get(j),context?.getExternalFilesDir(
                        Environment.DIRECTORY_PICTURES)!!))
            }
            productos.add(Product(
                jsonOb?.ads?.get(i)?.name.toString(),
                jsonOb?.ads?.get(i)?.title.toString(),
                jsonOb?.ads?.get(i)?.phone.toString(),
                jsonOb?.ads?.get(i)?.description.toString(),
                jsonOb?.ads?.get(i)?.price!!.toDouble(),
                0.0,
                arrFilesImages))
        }

        listProducts = binding.rvProducts
        listProducts?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.context)
        listProducts?.layoutManager = layoutManager
        listProducts?.adapter = AdaptadorCustom(productos, object: ClickListener {
            override fun onClick(vista: View, index: Int) {
                var extras = Bundle()
                extras.putSerializable("data",productos[index])
                extras.putBoolean("contact",true)

                val intent = Intent(requireContext(), ProductDetailActivity::class.java)
                intent.putExtras(extras)
                requireContext()?.startActivity(intent)
            }
        })
    }
}


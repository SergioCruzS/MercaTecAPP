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
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siuuu.mercatec.databinding.FragmentAdsBinding
import com.siuuu.mercatec.ui.detalle.ProductDetailActivity
import com.siuuu.mercatec.ui.home.ClickListener
import com.siuuu.mercatec.ui.home.Product
import com.siuuu.mercatec.ui.login.UserResponse
import com.siuuu.mercatec.ui.login.preference
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
        binding.ivLoadingAds.visibility = View.VISIBLE


        val queue = Volley.newRequestQueue(requireContext())
        val url = Strings.url_base + Strings.url_get_ad
        val json = object: JsonObjectRequest(
            Method.GET, url,
            JSONObject(),
            Response.Listener { response ->
                //println("resp: "+response.toString())
                addListAds(response)
                binding.ivLoadingAds.visibility = View.GONE
            },
            Response.ErrorListener { error -> Toast.makeText(context,"Error al obtener los datos", Toast.LENGTH_SHORT).show()}

        ){

        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String>? {
            val params: MutableMap<String, String> =
                HashMap()
            params["uid"] = preference.preferenceManager(requireContext()).getString("uid","null")!!
            return params
        }}
        json.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(json)

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
            var arrFilesImages = ArrayList<String>()
            for (url in jsonOb?.ads?.get(i)?.img?.indices!!){
                arrFilesImages.add(jsonOb?.ads?.get(i)?.img!!.get(url))
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

        listProducts = binding.rvAds
        listProducts?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.context)
        listProducts?.layoutManager = layoutManager
        listProducts?.adapter = CustomAdapterAds(requireContext(),productos, object: ClickListener {
            override fun onClick(vista: View, index: Int) {
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
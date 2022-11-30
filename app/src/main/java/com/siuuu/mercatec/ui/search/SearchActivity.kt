package com.siuuu.mercatec.ui.search
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siuuu.mercatec.R
import com.siuuu.mercatec.databinding.ActivitySearchBinding
import com.siuuu.mercatec.ui.ads.AdResponse
import com.siuuu.mercatec.ui.detalle.ProductDetailActivity
import com.siuuu.mercatec.ui.home.AdaptadorCustom
import com.siuuu.mercatec.ui.home.ClickListener
import com.siuuu.mercatec.ui.home.Product
import com.siuuu.mercatec.ui.values.ImageEncodeAndDecode
import com.siuuu.mercatec.ui.values.Strings
import org.json.JSONObject
import java.io.File


class SearchActivity : AppCompatActivity(){
    private lateinit var binding:ActivitySearchBinding
    lateinit var searchView: SearchView
    var listProducts: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarSearch)

        searchView = findViewById(R.id.sv_search)

        val productos2 = ArrayList<String>()
        productos2.add("Sergio")
        productos2.add("Kenia")
        productos2.add("Susana")
        productos2.add("Samuel")
        listProducts = binding.rvSearch
        listProducts?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        listProducts?.layoutManager = layoutManager

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val url = Strings.url_base + Strings.url_get_Search
                val queue = Volley.newRequestQueue(this@SearchActivity)
                val json = object: JsonObjectRequest(
                    Request.Method.GET, url,
                    JSONObject(),
                    Response.Listener { response ->
                        //println("resp: "+response.toString())
                        addListAds(response)
                        Toast.makeText(this@SearchActivity, "Consulta correcta", Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { error -> Toast.makeText(this@SearchActivity,"$error", Toast.LENGTH_SHORT).show()}

                ){
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String>? {
                        val params: MutableMap<String, String> =
                            HashMap()
                        params["query"] = query.toString()
                        return params
                    }}
                json.retryPolicy = DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                queue.add(json)

                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val queue = Volley.newRequestQueue(this@SearchActivity)
                queue.stop()
                return false
            }

        })

        binding.ivBackToolbarSearch.setOnClickListener(){
            this.onBackPressed()
        }
    }

    private fun addListAds(response: JSONObject){
        val productos = ArrayList<Product>()
        var jsonOb = AdResponse.jsonAd(response.toString())
        for (i in jsonOb?.ads?.indices!!){
            var arrFilesImages = ArrayList<File>()
            for (j in jsonOb?.ads?.get(i)?.img?.indices!!){
                arrFilesImages.add(
                    ImageEncodeAndDecode.decode(jsonOb?.ads?.get(i)?.img!!.get(j),this.getExternalFilesDir(
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

        listProducts = binding.rvSearch
        listProducts?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        listProducts?.layoutManager = layoutManager
        listProducts?.adapter = CustomAdapterSearch(productos, object: ClickListener {
            override fun onClick(vista: View, index: Int) {
                Toast.makeText(this@SearchActivity, productos[index].name, Toast.LENGTH_SHORT).show()
                var extras = Bundle()
                extras.putSerializable("data",productos[index])
                extras.putBoolean("contact",true)

                val intent = Intent(this@SearchActivity, ProductDetailActivity::class.java)
                intent.putExtras(extras)
                this@SearchActivity.startActivity(intent)
            }
        })
    }


}
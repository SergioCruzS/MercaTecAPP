package com.siuuu.mercatec.ui.search
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siuuu.mercatec.R
import com.siuuu.mercatec.databinding.ActivitySearchBinding
import com.siuuu.mercatec.ui.detalle.ProductDetailActivity
import com.siuuu.mercatec.ui.home.ClickListener
import com.siuuu.mercatec.ui.home.Product


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

        val productos = ArrayList<Product>()
        productos.add(Product("Sergio", "Arduino UNO", "Arduino UNO usado en perfectas condiciones",399.00,4.0,com.siuuu.mercatec.R.drawable.arduino_uno))
        productos.add(Product("Kenia", "Resistencias", "Resistencias de 180 y 220 ohms nuevas",1.50,4.5,com.siuuu.mercatec.R.drawable.resistencia_180))
        productos.add(Product("Susana", "Multímetro", "Marca truper, excelentes condiciones ",230.00,5.0,com.siuuu.mercatec.R.drawable.multimetro_truper))
        productos.add(Product("Samuel", "Resistencias", "Resistencias de 220 ohms nuevas",2.00,4.5,com.siuuu.mercatec.R.drawable.resistencia_220))
        productos.add(Product("Yadira", "Multímetro", "Marca SEAFON, excelentes condiciones ",100.00,3.7,com.siuuu.mercatec.R.drawable.multimetro_seafon))

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
                if (productos2.contains(query)){
                    listProducts?.adapter = CustomAdapterSearch(productos,object:ClickListener{
                        override fun onClick(vista: View, index: Int) {
                            val intent = Intent(this@SearchActivity, ProductDetailActivity::class.java)
                            this@SearchActivity.startActivity(intent)
                        }
                    })
                }else{
                    Toast.makeText(applicationContext,"No hay nada compa",Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Toast.makeText(applicationContext,"$query",Toast.LENGTH_SHORT).show()
                return false
            }

        })

        binding.ivBackToolbarSearch.setOnClickListener(){
            this.onBackPressed()
        }
    }


}
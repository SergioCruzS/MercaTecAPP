package com.siuuu.mercatec

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.siuuu.mercatec.databinding.ActivityMainBinding
import com.siuuu.mercatec.ui.detalle.ProductDetailActivity
import com.siuuu.mercatec.ui.login.Jwt
import com.siuuu.mercatec.ui.login.LoginActivity
import com.siuuu.mercatec.ui.login.LoginJSON
import com.siuuu.mercatec.ui.login.UserResponse
import com.siuuu.mercatec.ui.search.SearchActivity
import com.siuuu.mercatec.ui.values.Strings
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        setSupportActionBar(binding.appBarMain.toolbarMain)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_perfil, R.id.nav_ads
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val ivbar:ImageView = findViewById(R.id.iv_ic_search)
        ivbar.setOnClickListener(){
            Toast.makeText(this,"Buscar",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SearchActivity::class.java)
            this.startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
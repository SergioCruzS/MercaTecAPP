package com.siuuu.mercatec.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siuuu.mercatec.R
import com.siuuu.mercatec.databinding.ActivityRegisterBinding
import com.siuuu.mercatec.ui.values.strings
import org.json.JSONObject
import java.io.Console

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btRegister.setOnClickListener(){
            val queue = Volley.newRequestQueue(this)
            val url = strings.url_post_register
            val json = JsonObjectRequest(Request.Method.POST, url, JSONObject(RegisterJSON("ser","testser@test.com","1234","1234").toJson()),
                Response.Listener { response ->
                    println("resp: "+response.toString())
                },
                Response.ErrorListener { error -> println("error: "+error) }

            )
            queue.add(json)
            println(json.body)
        }
    }
}
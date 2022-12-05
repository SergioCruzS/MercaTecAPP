package com.siuuu.mercatec.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siuuu.mercatec.MainActivity
import com.siuuu.mercatec.R
import com.siuuu.mercatec.databinding.ActivityLoginBinding
import com.siuuu.mercatec.ui.values.Strings
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!preference.preferenceManager(this).getString("uid","null").equals("null")){
            val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            this?.startActivity(intent)
            finish()
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btIniciarSesionLogin.setOnClickListener(){
            val queue = Volley.newRequestQueue(this)
            val url = Strings.url_base+Strings.url_post_login
            val json = JsonObjectRequest(
                Request.Method.POST, url,
                JSONObject(LoginJSON(binding.tvEmailLogin.text.toString(),
                                     binding.tvPasswordLogin.text.toString()).toJson()),
                Response.Listener { response ->
                    Toast.makeText(this, "Inicio de Sesión Correcto", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    this?.startActivity(intent)
                    var jsonOb = UserResponseLogin.fromJson(response.toString())
                    preference.preferenceManager(this).edit().putString("uid",jsonOb?.uid.toString()).commit()
                    preference.preferenceManager(this).edit().putString("phone",jsonOb?.phone.toString()).commit()
                    preference.preferenceManager(this).edit().putString("name",jsonOb?.name.toString()).commit()
                    finish()
                },
                Response.ErrorListener { error -> Toast.makeText(this,"Verifique que sus datos sean correctos", Toast.LENGTH_LONG).show()}

            )
            queue.add(json)
        }

        binding.tvRegistrarLogin.setOnClickListener(){
            val intent = Intent(this,RegisterActivity::class.java)
            this?.startActivity(intent)
            finish()
        }
        var visibility = false;
        binding.ivVisibilityPasswordLogin.setOnClickListener(){
            //145 = texto visible
            //129 = texto invisible
            if (visibility){
                binding.ivVisibilityPasswordLogin.setImageResource(R.drawable.ic_visibility)
                binding.tvPasswordLogin.inputType = 129
                visibility = false
            }else{
                binding.ivVisibilityPasswordLogin.setImageResource(R.drawable.ic_visibility_off)
                binding.tvPasswordLogin.inputType = 145
                visibility = true
            }
        }
    }
}
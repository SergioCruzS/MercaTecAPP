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
import com.siuuu.mercatec.databinding.ActivityRegisterBinding
import com.siuuu.mercatec.ui.values.Strings
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        binding.btRegister.setOnClickListener(){
            val queue = Volley.newRequestQueue(this)
            val url = Strings.url_post_register
            val json = JsonObjectRequest(Request.Method.POST, url,
                JSONObject(RegisterJSON(
                                        binding.tvNameRegister.text.toString(),
                                        binding.tvEmailRegister.text.toString(),
                                        binding.tvPasswordRegister.text.toString(),
                                        binding.tvConfirmPasswordRegister.text.toString()).toJson()),
                Response.Listener { response ->
                    //println("resp: "+response.toString())
                    Toast.makeText(this, "Registrado Correctamente",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    this?.startActivity(intent)
                    var jsonOb = UserResponse.userFromJson(response.toString())
                    val editor = prefs.edit()
                    editor.putString("uid",jsonOb?.newUser?.uid)
                    editor.apply()
                    finish()
                },
                Response.ErrorListener { error -> Toast.makeText(this,"$error",Toast.LENGTH_SHORT).show() }

            )
            queue.add(json)
        }

        binding.tvLoginRegister.setOnClickListener(){
            val intent = Intent(this,LoginActivity::class.java)
            this?.startActivity(intent)
            finish()
        }

        var visibilityPassword = false;
        binding.ivVisibilityPasswordRegister.setOnClickListener(){
            //145 = texto visible
            //129 = texto invisible
            if (visibilityPassword){
                binding.ivVisibilityPasswordRegister.setImageResource(R.drawable.ic_visibility)
                binding.tvPasswordRegister.inputType = 129
                visibilityPassword = false
            }else{
                binding.ivVisibilityPasswordRegister.setImageResource(R.drawable.ic_visibility_off)
                binding.tvPasswordRegister.inputType = 145
                visibilityPassword = true
            }
        }

        var visibilityConfirmPassword = false;
        binding.ivVisibilityConfirmPasswordRegister.setOnClickListener(){
            //145 = texto visible
            //129 = texto invisible
            if (visibilityConfirmPassword){
                binding.ivVisibilityConfirmPasswordRegister.setImageResource(R.drawable.ic_visibility)
                binding.tvConfirmPasswordRegister.inputType = 129
                visibilityConfirmPassword = false
            }else{
                binding.ivVisibilityConfirmPasswordRegister.setImageResource(R.drawable.ic_visibility_off)
                binding.tvConfirmPasswordRegister.inputType = 145
                visibilityConfirmPassword = true
            }
        }
    }
}
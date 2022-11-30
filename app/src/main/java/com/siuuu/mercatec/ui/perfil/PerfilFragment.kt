package com.siuuu.mercatec.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siuuu.mercatec.MainActivity
import com.siuuu.mercatec.databinding.FragmentPerfilBinding
import com.siuuu.mercatec.ui.login.*
import com.siuuu.mercatec.ui.values.Strings
import org.json.JSONObject

class PerfilFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    lateinit var binding: FragmentPerfilBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val uid = preference.preferenceManager(requireContext()).getString("uid","null").toString()
        val queue = Volley.newRequestQueue(context)
        val url = Strings.url_base+Strings.url_get_user
        val json = object : JsonObjectRequest(
            Request.Method.GET, url,
            JSONObject(),
            Response.Listener { response ->
                //println("resp: "+response.toString())
                val jsonOb = UserResponse.userFromJson(response.toString())
                var user = jsonOb?.newUser
                binding.tvNamePerfil.text.clear()
                binding.tvEmailPerfil.text.clear()
                binding.tvPhonePerfil.text.clear()
                binding.tvPasswordPerfil.text.clear()
                binding.tvConfirmPasswordPerfil.text.clear()
                binding.tvNamePerfil.text.insert(0,user?.name)
                binding.tvEmailPerfil.text.insert(0,user?.email)
                binding.tvPhonePerfil.text.insert(0,user?.phone)
            },
            Response.ErrorListener { error -> Toast.makeText(context,"$error", Toast.LENGTH_SHORT).show()}

        ){
        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String>? {
            val params: MutableMap<String, String> =
                HashMap()
            params["uid"] = uid
            return params
        }}
        json.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(json)

        binding.btLogoutPerfil.setOnClickListener(){
            preference.preferenceManager(requireContext()).edit().remove("uid").commit()
            preference.preferenceManager(requireContext()).edit().remove("phone").commit()
            preference.preferenceManager(requireContext()).edit().remove("name").commit()
            val intent = Intent(context, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            this?.startActivity(intent)
        }

        binding.ivEditPhone.setOnClickListener(){
            binding.tvPhonePerfil.isEnabled = !binding.tvPhonePerfil.isEnabled
        }

        binding.ivEditPassword.setOnClickListener(){
            binding.tvPasswordPerfil.isEnabled = !binding.tvPasswordPerfil.isEnabled
            binding.tvConfirmPasswordPerfil.isEnabled = !binding.tvConfirmPasswordPerfil.isEnabled
        }



        binding.btUpdatePerfil.setOnClickListener(){
            var validation = true
            if (binding.tvPhonePerfil.text.equals("")){
                Toast.makeText(context,"El número no debe estar vacío",Toast.LENGTH_SHORT).show()
                validation = false
            }
            if ((binding.tvPasswordPerfil.text.toString().compareTo(binding.tvConfirmPasswordPerfil.text.toString())) != 0){
                Toast.makeText(context,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show()
                validation = false
            }

            if (validation){
                val queue = Volley.newRequestQueue(requireContext())
                val url = Strings.url_base + Strings.url_post_update
                val json = JsonObjectRequest(Request.Method.POST, url,
                    JSONObject(UpdateJSON(
                        preference.preferenceManager(requireContext()).getString("uid", "null").toString(),
                        binding.tvPhonePerfil.text.toString(),
                        binding.tvPasswordPerfil.text.toString()
                    ).toJson()),
                    Response.Listener { response ->
                        //println("resp: "+response.toString())
                        Toast.makeText(requireContext(), "Actualizado Correctamente",Toast.LENGTH_SHORT).show()
                        preference.preferenceManager(requireContext()).edit().putString("phone",binding.tvPhonePerfil.text.toString()).commit()
                    },
                    Response.ErrorListener { error -> Toast.makeText(requireContext(),"$error",Toast.LENGTH_SHORT).show() }

                )
                queue.add(json)
            }
        }

        return root
    }

}
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

        val queue = Volley.newRequestQueue(context)
        val url = Strings.url_base+Strings.url_get_user
        val json = object : JsonObjectRequest(
            Request.Method.GET, url,
            JSONObject(),
            Response.Listener { response ->
                //println("resp: "+response.toString())
                val jsonOb = UserResponse.userFromJson(response.toString())
                var user = jsonOb?.newUser
                binding.tvNamePerfil.text.insert(0,user?.name)
                binding.tvEmailPerfil.text.insert(0,user?.email)
                binding.tvPhonePerfil.text.insert(0,user?.phone)

                Toast.makeText(context, "Consulta correcta", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error -> Toast.makeText(context,"$error", Toast.LENGTH_SHORT).show()}

        ){
        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String>? {
            val params: MutableMap<String, String> =
                HashMap()
            params["uid"] = preference.preferenceManager(context!!).getString("uid","null").toString()
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
            val intent = Intent(context, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            this?.startActivity(intent)
        }

        return root
    }

}
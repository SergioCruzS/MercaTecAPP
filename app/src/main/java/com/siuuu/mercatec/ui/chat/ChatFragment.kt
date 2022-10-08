package com.siuuu.mercatec.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.siuuu.mercatec.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        //PENDIENTEEEEE
        //var buttonBack: Button? = binding.btProductDetailBack


        //buttonBack?.setOnClickListener {
        //    println("listener")
        //    //Fragment Transactions
        //    val fragmentManager = activity?.supportFragmentManager
        //    // Create and commit a new transaction
        //    val transaction = fragmentManager?.beginTransaction()
        //    transaction?.replace(R.id.nav_host_fragment_content_main, HomeFragment())
        //    transaction?.commit()
        //}
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
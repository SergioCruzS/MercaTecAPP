package com.siuuu.mercatec.ui.chat

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.siuuu.mercatec.R
import com.siuuu.mercatec.databinding.ActivityChatBinding


class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarChat)


    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
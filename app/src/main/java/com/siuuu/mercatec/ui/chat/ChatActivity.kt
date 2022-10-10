package com.siuuu.mercatec.ui.chat

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siuuu.mercatec.R
import com.siuuu.mercatec.databinding.ActivityChatBinding


class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    var listMessages: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarChat)
        var arrMsg = ArrayList<Message>()
        arrMsg.add(0,Message("Hola",0))
        arrMsg.add(1,Message("Me puedes dar informes",0))
        arrMsg.add(2,Message("Hola,claro",1))
        arrMsg.add(3,Message("En un momento le doy información",1))
        arrMsg.add(4,Message("Gracias",0))
        arrMsg.add(5,Message("Tengo resistencias de 220 y 180 ohms nuevas para entrega inmediata",1))
        arrMsg.add(6,Message("Que precio?",0))
        arrMsg.add(7,Message("1 peso cada una",1))
        arrMsg.add(8,Message("quiero 10 de 220 y 5 de 180 por favor",0))
        arrMsg.add(9,Message("Claro, te encuentras en el tec?",1))


        listMessages = binding.rvChat
        listMessages?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.applicationContext)
        listMessages?.layoutManager = layoutManager
        listMessages?.adapter = AdaptadorCustomChat(arrMsg)



    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
package com.siuuu.mercatec.ui.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siuuu.mercatec.R
import com.siuuu.mercatec.databinding.ReceiveMessageBinding
import com.siuuu.mercatec.databinding.SendMessageBinding

class AdaptadorCustomChat (messages:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder?>(){
    lateinit var messages: ArrayList<Message>
    private val ITEM_SENT = 0
    private val ITEM_RECEIVE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT){
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.send_message, parent,false)
            SentMsgHolder(view)
        }else{
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.receive_message, parent,false)
            ReceiveMsgHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = messages[position]
        return if (item.type == 0){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = messages[position]
        if (holder.javaClass == SentMsgHolder::class.java){
            val viewHolder = holder as SentMsgHolder
            viewHolder.binding.tvSendMessage.text = item.msg
        }else{
            val viewHolder = holder as ReceiveMsgHolder
            viewHolder.binding.tvReceiveMessage.text = item.msg
        }
    }

    override fun getItemCount(): Int = messages.size

    inner class SentMsgHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var binding:SendMessageBinding = SendMessageBinding.bind(itemView)
    }

    inner class ReceiveMsgHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var binding:ReceiveMessageBinding = ReceiveMessageBinding.bind(itemView)
    }

    init {
        if (messages != null){
            this.messages = messages
        }
    }




}
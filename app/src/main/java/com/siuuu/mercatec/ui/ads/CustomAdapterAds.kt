package com.siuuu.mercatec.ui.ads

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siuuu.mercatec.R
import com.siuuu.mercatec.ui.home.ClickListener
import com.siuuu.mercatec.ui.home.Product

class CustomAdapterAds(items:ArrayList<Product>, var listener: ClickListener): RecyclerView.Adapter<CustomAdapterAds.ViewHolder>() {

    var items: ArrayList<Product>? = null
    init {
        this.items = items
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.card_ad, parent,false)
        return ViewHolder(vista, listener)
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.image?.setImageResource(item?.image!!)
        holder.name?.text = item?.name
        holder.price?.text = "$ "+item?.price.toString() + " mxn"
    }

    class ViewHolder(vista: View, listener: ClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener{
        var image: ImageView? = null
        var name: TextView? = null
        var price: TextView? = null
        var listener: ClickListener? = null

        init {
            image = vista.findViewById(R.id.iv_product_ad)
            name = vista.findViewById(R.id.tv_title_ad)
            price = vista.findViewById(R.id.tv_price_ad)
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }

}
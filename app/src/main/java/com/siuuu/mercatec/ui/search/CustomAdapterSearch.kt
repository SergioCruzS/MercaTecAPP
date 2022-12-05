package com.siuuu.mercatec.ui.search;

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.siuuu.mercatec.R
import com.siuuu.mercatec.ui.home.ClickListener
import com.siuuu.mercatec.ui.home.Product


class CustomAdapterSearch(context:Context, items:ArrayList<Product>, var listener:ClickListener): RecyclerView.Adapter<CustomAdapterSearch.ViewHolder>() {
    var items: ArrayList<Product>? = null
    lateinit var context:Context

    init {
        this.items = items
        this.context=context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.card_search, parent,false)
        return ViewHolder(vista, listener)
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        Glide.with(context).load(item?.image?.get(0)).fitCenter().centerCrop().into(holder.image!!)
        holder.title?.text = item?.name
        holder.price?.text = "$ "+item?.price.toString() + " mxn"
    }

    class ViewHolder(vista:View, listener: ClickListener):RecyclerView.ViewHolder(vista),View.OnClickListener{
        var image:ImageView? = null
        var title:TextView? = null
        var price:TextView? = null
        var listener:ClickListener? = null

        init {
            image = vista.findViewById(R.id.iv_product_search)
            title = vista.findViewById(R.id.tv_title_search)
            price = vista.findViewById(R.id.tv_price_and_available_search)
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }

}

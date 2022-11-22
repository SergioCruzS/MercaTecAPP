package com.siuuu.mercatec.ui.search;

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siuuu.mercatec.R
import com.siuuu.mercatec.ui.home.ClickListener
import com.siuuu.mercatec.ui.home.Product


class CustomAdapterSearch(items:ArrayList<Product>, var listener:ClickListener): RecyclerView.Adapter<CustomAdapterSearch.ViewHolder>() {
    var items: ArrayList<Product>? = null

    init {
        this.items = items
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
        holder.image?.setImageBitmap(BitmapFactory.decodeFile(item?.image?.get(0)?.getAbsolutePath()))
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

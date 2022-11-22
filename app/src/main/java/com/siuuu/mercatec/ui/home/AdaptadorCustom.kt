package com.siuuu.mercatec.ui.home

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siuuu.mercatec.R


class AdaptadorCustom(items:ArrayList<Product>, var listener: ClickListener): RecyclerView.Adapter<AdaptadorCustom.ViewHolder>() {

    var items: ArrayList<Product>? = null
    init {
        this.items = items
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.card_producto, parent,false)
        return ViewHolder(vista, listener)
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.image?.setImageBitmap(BitmapFactory.decodeFile(item?.image?.get(0)?.getAbsolutePath()))
        holder.user?.text = item?.user
        holder.name?.text = item?.name
        holder.description?.text = item?.description
        holder.price?.text = "$ "+item?.price.toString() + " mxn"
        holder.rating?.rating = item?.rating?.toFloat()!!
    }

    class ViewHolder(vista:View, listener: ClickListener):RecyclerView.ViewHolder(vista),View.OnClickListener{
        var image:ImageView? = null
        var user:TextView? = null
        var name:TextView? = null
        var description:TextView? = null
        var price:TextView? = null
        var rating:RatingBar? = null
        var listener:ClickListener? = null

        init {
            image = vista.findViewById(R.id.imv_producto)
            user = vista.findViewById(R.id.txv_usuario)
            name = vista.findViewById(R.id.txv_titulo)
            description = vista.findViewById(R.id.txv_descripcion)
            price = vista.findViewById(R.id.txv_precio)
            rating = vista.findViewById(R.id.rb_rating)
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }

}
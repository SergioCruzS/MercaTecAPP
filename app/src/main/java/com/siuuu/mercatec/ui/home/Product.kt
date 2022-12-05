package com.siuuu.mercatec.ui.home

import java.io.File

class Product (user:String, name:String, phone:String,description:String, price: Double, rating: Double, image: ArrayList<String>) : java.io.Serializable{

    var user = ""
    var name = ""
    var phone = ""
    var description = ""
    var price = 0.0
    var rating = 0.0
    var image: ArrayList<String>? = null

    init {
        this.user = user
        this.name = name
        this.phone = phone
        this.description = description
        this.price = price
        this.rating = rating
        this.image = image
    }
}
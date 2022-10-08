package com.siuuu.mercatec.ui.home

class Product (user:String, name:String, description:String, price: Double, rating: Double, image:Int) {

    var user = ""
    var name = ""
    var description = ""
    var price = 0.0
    var rating = 0.0
    var image = 0

    init {
        this.user = user
        this.name = name
        this.description = description
        this.price = price
        this.rating = rating
        this.image = image
    }
}
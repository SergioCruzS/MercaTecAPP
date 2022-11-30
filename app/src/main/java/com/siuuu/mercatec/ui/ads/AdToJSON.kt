package com.siuuu.mercatec.ui.ads

import com.beust.klaxon.*

private val klaxon = Klaxon()

data class AdToJSON (
    val uid: String,
    val name: String,
    val phone: String,
    val title: String,
    val price: String,
    val description: String,
    val img: List<String>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<AdToJSON>(json)
    }
}
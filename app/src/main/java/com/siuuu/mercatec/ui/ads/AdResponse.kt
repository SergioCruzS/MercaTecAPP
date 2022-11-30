package com.siuuu.mercatec.ui.ads

import com.beust.klaxon.*

private val klaxon = Klaxon()

data class AdResponse (
    val ok: Boolean,
    val ads: List<Ad>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun jsonAd(json: String) = klaxon.parse<AdResponse>(json)
    }
}

data class Ad (
    val uid: String,
    val name: String,
    val phone: String,
    val title: String,
    val price: String,
    val description: String,
    val img: List<String>
)

data class DeleteAd (
    val uid: String,
    val title: String
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun jsonAd(json: String) = klaxon.parse<DeleteAd>(json)
    }
}
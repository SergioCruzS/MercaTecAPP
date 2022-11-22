package com.siuuu.mercatec.ui.ads

import com.beust.klaxon.*

private val klaxon = Klaxon()

data class AdResponse (
    val ok: Boolean,
    val ads: List<Ad>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<AdResponse>(json)
    }
}

data class Ad (
    @Json(name = "_id")
    val id: String,

    val uid: String,
    val title: String,
    val price: String,
    val description: String,
    val img: List<String>,

    @Json(name = "__v")
    val v: Long
)
package com.siuuu.mercatec.ui.login

import com.beust.klaxon.Klaxon

data class Jwt (
    val ok: String,
    val uid: String,
    val token: String
){

    fun toJson() = Klaxon().toJsonString(this)

    companion object {
        fun fromJson(json: String) = Klaxon().parse<Jwt>(json)
    }
}
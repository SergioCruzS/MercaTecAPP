package com.siuuu.mercatec.ui.login
import com.beust.klaxon.*

data class LoginJSON (
    val email: String,
    val password: String
        ){

    fun toJson() = Klaxon().toJsonString(this)

    companion object {
        fun fromJson(json: String) = Klaxon().parse<LoginJSON>(json)
    }
}
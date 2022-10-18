package com.siuuu.mercatec.ui.login
import com.beust.klaxon.*

data class RegisterJSON (
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
) {
    fun toJson() = Klaxon().toJsonString(this)

    companion object {
        fun fromJson(json: String) = Klaxon().parse<RegisterJSON>(json)
    }
}
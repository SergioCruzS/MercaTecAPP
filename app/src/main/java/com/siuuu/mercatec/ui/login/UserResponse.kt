package com.siuuu.mercatec.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.beust.klaxon.*

private val klaxon = Klaxon()

data class UserResponse (
    val ok: String,
    val newUser: NewUser,
    val token: String
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun userFromJson(json: String) = klaxon.parse<UserResponse>(json)
    }
}

data class NewUser (
    val name: String,
    val email: String,
    val phone: String,
    val active: Boolean,
    val uid: String
)

data class UserResponseLogin (
    val ok: String,
    val uid: String,
    val token: String
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<UserResponseLogin>(json)
    }
}

data class preference(
    val context: Context
){
    companion object{
        fun preferenceManager(context: Context):SharedPreferences{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs
        }
    }
}

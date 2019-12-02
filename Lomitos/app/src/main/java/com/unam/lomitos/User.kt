package com.unam.lomitos

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class User(val  status: String, val key: String){
    class Deserializer: ResponseDeserializable<User> {
        override fun deserialize(content: String): User? = Gson().fromJson(content, User::class.java)
    }
}
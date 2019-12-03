package com.unam.lomitos
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {


    @GET("login.php?username={username}&password={password}")
    fun getKey(@Path("username") username: String, @Path("password") password:String): Call<User>

}
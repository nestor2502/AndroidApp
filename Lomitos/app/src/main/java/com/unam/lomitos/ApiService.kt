package com.unam.lomitos
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("posts/")
    fun getAllPosts(): Call<List<POST>>

    @GET("login.php?username={username}&password={password}")
    fun getKey(@Path("username") username: String, @Path("password") password:String): Call<User>

    @POST("posts/{id}")
    fun editPostById(@Path("id") id: Int, @Body post: POST?): Call<POST>
}
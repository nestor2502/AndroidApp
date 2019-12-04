package com.unam.lomitos

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

@SuppressLint("StaticFieldLeak")
object VolleyService {
    private lateinit var context: Context

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(context) }

    val imageLoader: ImageLoader by lazy { ImageLoader(requestQueue, LruBitmapCache()) }

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
}
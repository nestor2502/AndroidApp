package com.unam.lomitos

import android.content.Context
import com.android.volley.RequestQueue

import com.android.volley.toolbox.Volley

class VolleyService {
    private lateinit var context: Context

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(context) }

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
}
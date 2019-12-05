package com.unam.lomitos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.NetworkImageView
import org.json.JSONArray
import org.json.JSONObject


class feed : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val url = "http://lomitos-api.tk/feed.php?key=%s".format(intent.getStringExtra("key"))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        VolleyService.initialize(this)
        val recycler_view = findViewById(R.id.recycler) as RecyclerView
        recycler_view.layoutManager = LinearLayoutManager(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                recycler_view.adapter = AdaptadorPost(response.getJSONArray("posts"),
                this, intent.getStringExtra("key"))
            },
            Response.ErrorListener {
                Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
            })
        VolleyService.requestQueue.add(request)
    }
}
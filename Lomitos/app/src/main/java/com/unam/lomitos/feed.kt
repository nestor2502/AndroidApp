package com.unam.lomitos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class feed : AppCompatActivity() {

    val post_list = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val url = "http://lomitos-api.tk/feed.php?key=%s".format(intent.getStringExtra("key"))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        VolleyService.initialize(this)
        var posts:JSONArray = JSONArray()
        val recycler_view = findViewById<RecyclerView>(R.id.recycler)
        recycler_view.layoutManager = LinearLayoutManager(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                recycler_view.adapter = AdaptadorPost(response.getJSONArray("posts"),this, intent.getStringExtra("key"))
            },
            Response.ErrorListener {
                Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
            })
        VolleyService.requestQueue.add(request)
    }
}
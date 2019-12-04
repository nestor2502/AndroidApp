package com.unam.lomitos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.kittinunf.fuel.android.core.Json
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_comments.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

class Comments : AppCompatActivity() {

    private var adaptadorComentarios: RecyclerView.Adapter<RecyclerView.ViewHolder>? ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        //val intent = intent

        val toolbar =findViewById<androidx.appcompat.widget.Toolbar>(R.id.bar)
        toolbar.setTitle("Comentarios")
        setSupportActionBar(toolbar)

        //val key = intent.getStringExtra("key")
        val key = "3e8e656cc5ad2a43d30e2040c3182239db66243311df5c7ae456f90f824d07dc"

        //val id = intent.getStringExtra("id")
        val id = "1"

        val recycler:RecyclerView = findViewById(R.id.recyclerViewComments)

        recycler.layoutManager = LinearLayoutManager(this)

        var cola = Volley.newRequestQueue(this)
        val url = "http://www.lomitos-api.tk/detalles.php?userkey=paramkey&dog_id=dogid"
            .replace("paramkey",key).replace("dogid",id)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            com.android.volley.Response.Listener<JSONObject>{ reponse ->
                recycler.adapter = CommentsAdapter(reponse.getJSONArray("comentarios"))
            },
            com.android.volley.Response.ErrorListener {
                Toast.makeText(this, "No funcionaaaa", Toast.LENGTH_SHORT).show()
            })

        cola.add(request)


    }

}

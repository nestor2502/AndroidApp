package com.unam.lomitos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Comments : AppCompatActivity() {

    private var adaptadorComentarios: RecyclerView.Adapter<RecyclerView.ViewHolder>? ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        val toolbar =findViewById<androidx.appcompat.widget.Toolbar>(R.id.bar)
        toolbar.setTitle("Comentarios")
        setSupportActionBar(toolbar)
        val key = intent.getStringExtra("key")
        val id = intent.getStringExtra("id")
        val recycler:RecyclerView = findViewById(R.id.recyclerViewComments)
        recycler.layoutManager = LinearLayoutManager(this)

        val cola = Volley.newRequestQueue(this)
        val url = "http://www.lomitos-api.tk/detalles.php?userkey=paramkey&dog_id=dogid"
            .replace("paramkey",key).replace("dogid",id)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            com.android.volley.Response.Listener<JSONObject>{ reponse ->
                recycler.adapter = CommentsAdapter(reponse.getJSONArray("comentarios"))
            },
            com.android.volley.Response.ErrorListener {
                Toast.makeText(this, "No hay conexión", Toast.LENGTH_SHORT).show()
            })

        cola.add(request)
    }

}

package com.unam.lomitos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
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
import android.content.DialogInterface
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AlertDialog


class feed : AppCompatActivity() {

    val url = "http://lomitos-api.tk/feed.php?key=3e8e656cc5ad2a43d30e2040c3182239db66243311df5c7ae456f90f824d07dc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        VolleyService.initialize(this)
        val recycler_view = findViewById(R.id.recycler) as RecyclerView
        recycler_view.layoutManager = LinearLayoutManager(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                recycler_view.adapter = AdaptadorPost(response.getJSONArray("posts"),this)
            },
            Response.ErrorListener {
                Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
            })
        VolleyService.requestQueue.add(request)
    }

    class AdaptadorPost(var posts: JSONArray, val context: Context):RecyclerView.Adapter<AdaptadorPost.ViewHolder>() {
        val onItemClickListener: OnItemClickListener? = null

        override fun onBindViewHolder(holder:AdaptadorPost.ViewHolder, position: Int) {
            holder.bind_Items(post=posts.getJSONObject(position))
            holder.paw?.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "lo que sea",Toast.LENGTH_SHORT).show()
            })

            holder.comment?.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "lo que sea",Toast.LENGTH_SHORT).show()

                fun onTouch(v:View,event:MotionEvent):Boolean {
                    displayAlert();
                    return false;
                }
            })

            holder.more?.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "lo que sea",Toast.LENGTH_SHORT).show()
            })
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.post, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return posts.length()
        }

        fun displayAlert() {
            AlertDialog.Builder(this).setMessage("Hi , I am Alert Dialog")
                .setTitle("My Alert")
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                    DialogInterface.OnClickListener { dialog, whichButton -> finish() })
                .show()
        }

        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

            var paw: ImageButton? = null
            var comment: ImageButton? = null
            var more: ImageButton? = null

            @SuppressLint("WrongViewCast")
            fun bind_Items(post:JSONObject) {
                val nombre = view.findViewById(R.id.nombre) as TextView
                val likes = view.findViewById(R.id.likes) as TextView
                val imagen = view.findViewById(R.id.foto) as NetworkImageView
                val id = view.findViewById(R.id.id_dog) as TextView
                paw = view.findViewById(R.id.paw) as ImageButton
                comment = view.findViewById(R.id.comentar) as ImageButton
                more = view.findViewById(R.id.detalles) as ImageButton
                nombre.text = post.getString("Nombre")
                likes.text = post.getString("Likes")
                id.text = post.getString("Id")
                imagen.setImageUrl(post.getString("Imagen"),VolleyService.imageLoader)
            }

            fun refresh_likes() {

            }
        }
    }
}
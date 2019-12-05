package com.unam.lomitos

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONObject

class AdaptadorPost(var posts: JSONArray, val context: Context, val key:String): RecyclerView.Adapter<AdaptadorPost.ViewHolder>(), Dialogo.Comentario {
    //val onItemClickListener: AdapterView.OnItemClickListener? = null

    override fun onBindViewHolder(holder:AdaptadorPost.ViewHolder, position: Int) {
        holder.bind_Items(post=posts.getJSONObject(position),key = key)

        holder.paw?.setOnClickListener(View.OnClickListener {
            holder.like(key)
            holder.refresh_likes(key)
            holder.refresh_paw(key)
        })

        holder.comment?.setOnClickListener(View.OnClickListener {
            val id = holder.view.findViewById(R.id.id_dog) as TextView
            Dialogo(context,this,id = id.text.toString())
        })

        holder.more?.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Comments::class.java)
            intent.putExtra("id", holder.view.findViewById<TextView>(R.id.id_dog).text.toString())
            intent.putExtra("key", key)
            startActivity(context,intent, Bundle.EMPTY)
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

    override fun comentar(comentario: String, id:String) {
        val url = "http://lomitos-api.tk/comment.php?userkey=%s&dog_id=%s".format(key,id)
        VolleyService.initialize(context)
        val postRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
            },
            Response.ErrorListener {
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["comment"] = comentario
                return params
            }
        }
        VolleyService.requestQueue.add(postRequest)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        var paw: ImageButton? = null
        var comment: ImageButton? = null
        var more: ImageButton? = null

        @SuppressLint("WrongViewCast")
        fun bind_Items(post: JSONObject, key: String) {
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
            refresh_likes(key)
            refresh_paw(key)
        }

        fun like(key: String) {
            val id = view.findViewById<TextView>(R.id.id_dog).text.toString()
            val url = "http://lomitos-api.tk/like.php?userkey=%s&dog_id=%s".format(key,id)
            val request = StringRequest(url,
                Response.Listener<String> {
                },
                Response.ErrorListener {
                })
            VolleyService.requestQueue.add(request)
        }

        fun refresh_paw(key: String) {
            val url = "http://lomitos-api.tk/iflike.php?key=%s&id=%s"
            val paw = itemView.findViewById<ImageView>(R.id.paw)
            val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    if (response["status"].toString() == "true")
                        paw.setImageResource(R.drawable.paw_like)
                    else
                        paw.setImageResource(R.drawable.paw)
                },
                Response.ErrorListener {
                    Toast.makeText(itemView.context, "That didn't work!", Toast.LENGTH_SHORT).show()
                })
            VolleyService.requestQueue.add(request)
        }

        fun refresh_likes(key: String) {
            val id = itemView.findViewById<TextView>(R.id.id_dog).text.toString()
            val url = "http://lomitos-api.tk/perro.php?key=%s&id=%s".format(key,id)
            val likes = itemView.findViewById<TextView>(R.id.likes)
            var paws:String? = null
            val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    likes.text = response["likes"].toString()
                },
                Response.ErrorListener {
                    Toast.makeText(itemView.context, "That didn't work!", Toast.LENGTH_SHORT).show()
                })
            VolleyService.requestQueue.add(request)
        }
    }
}

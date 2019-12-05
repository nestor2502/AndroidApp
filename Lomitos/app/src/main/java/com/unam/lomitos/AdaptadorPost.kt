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

class AdaptadorPost(val posts_json: JSONArray, val context: Context, val key:String): RecyclerView.Adapter<AdaptadorPost.ViewHolder>(), Dialogo.Comentario {

    private val posts = ArrayList<Post>()
    init {
        for (i in 0..9) {
            val post = posts_json.getJSONObject(i)
            posts.add(Post(post.getString("Id"),post.getString("Nombre"),
                post.getString("Imagen"),post.getString("Likes"),post.getString("Like")))
        }
    }

    override fun onBindViewHolder(holder:AdaptadorPost.ViewHolder, position: Int) {
        if (position == posts.size-1)
            add_post(position)
        holder.bind_Items(post = posts[position], key = key)
        holder.paw?.setOnClickListener(View.OnClickListener {
            holder.like(posts[position])
        })

        holder.comment?.setOnClickListener(View.OnClickListener {
            Dialogo(context,this,id = posts[position].id)
        })

        holder.more?.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Comments::class.java)
            intent.putExtra("id", posts[position].id)
            intent.putExtra("key", key)
            startActivity(context,intent, Bundle.EMPTY)
        })
        holder.bind_Items(post = posts[position], key = key)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.post, parent, false)
        return ViewHolder(view,key)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun comentar(comentario: String, id:String) {
        val url = "http://lomitos-api.tk/comment.php?userkey=%s&dog_id=%s".format(key,id)
        VolleyService.initialize(context)
        val postRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener {
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

    fun add_post(position: Int) {
        if ((position+10) < posts_json.length()) {
            for (i in (position+1)..(position+10)) {
                val post = posts_json.getJSONObject(i)
                posts.add(Post(post.getString("Id"),post.getString("Nombre"),
                    post.getString("Imagen"),post.getString("Likes"),post.getString("Like")))
            }
        } else {
            for (i in (position+1)..posts_json.length()-1) {
                val post = posts_json.getJSONObject(i)
                posts.add(Post(post.getString("Id"),post.getString("Nombre"),
                    post.getString("Imagen"),post.getString("Likes"),post.getString("Like")))
            }
        }
    }

    class ViewHolder(val view: View, val key: String) : RecyclerView.ViewHolder(view) {

        var paw: ImageButton? = null
        var comment: ImageButton? = null
        var more: ImageButton? = null
        private var like:Boolean = true

        @SuppressLint("WrongViewCast")
        fun bind_Items(post: Post, key: String) {
            like = post.like == "true"
            val nombre = view.findViewById(R.id.nombre) as TextView
            val likes = view.findViewById(R.id.likes) as TextView
            val imagen = view.findViewById(R.id.foto) as NetworkImageView
            val id = view.findViewById(R.id.id_dog) as TextView
            paw = view.findViewById(R.id.paw) as ImageButton
            comment = view.findViewById(R.id.comentar) as ImageButton
            more = view.findViewById(R.id.detalles) as ImageButton
            nombre.text = post.nombre
            likes.text = post.likes
            id.text = post.id
            imagen.setImageUrl(post.imagen,VolleyService.imageLoader)
        }

        fun like(post: Post) {
            val likes = itemView.findViewById<TextView>(R.id.likes)
            val id = post.id
            val url = "http://lomitos-api.tk/like.php?userkey=%s&dog_id=%s".format(key,id)
            val request = StringRequest(url,
                Response.Listener<String> {
                },
                Response.ErrorListener {
                    return@ErrorListener
                })
            VolleyService.requestQueue.add(request)
            if (like) {
                likes.text = (post.likes.toInt() - 1).toString()
                post.likes = (post.likes.toInt() - 1).toString()
                post.like = "false"
            } else {
                likes.text = (post.likes.toInt() + 1).toString()
                post.likes = (post.likes.toInt() + 1).toString()
                post.like = "true"
            }
            like = !like
        }
    }
}

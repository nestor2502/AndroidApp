package com.unam.lomitos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONObject

class AdaptadorPost(var posts: JSONArray, val context: Context, val key:String): RecyclerView.Adapter<AdaptadorPost.ViewHolder>(), Dialogo.Comentario {
    //val onItemClickListener: AdapterView.OnItemClickListener? = null

    override fun onBindViewHolder(holder:AdaptadorPost.ViewHolder, position: Int) {
        holder.bind_Items(post=posts.getJSONObject(position))

        holder.paw?.setOnClickListener(View.OnClickListener {
            Toast.makeText(context,holder.view.findViewById<TextView>(R.id.id_dog).text.toString(), Toast.LENGTH_SHORT).show()
        })

        holder.comment?.setOnClickListener(View.OnClickListener {
            val id = holder.view.findViewById(R.id.id_dog) as TextView
            Dialogo(context,this,id = id.text.toString())
        })

        holder.more?.setOnClickListener(View.OnClickListener {
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
        fun bind_Items(post: JSONObject) {
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
    }
}

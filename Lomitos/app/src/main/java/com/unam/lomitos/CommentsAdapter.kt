package com.unam.lomitos

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject

class CommentsAdapter(private val contenido:JSONArray) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflarLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_comments,parent,false)

        return ViewHolder(inflarLayout)
    }

    override fun getItemCount(): Int {
        return contenido.length()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind_comments(contenido[position] as JSONObject)

    }

    inner class ViewHolder(item:View): RecyclerView.ViewHolder(item){

        var nombreUsuario: TextView = itemView.findViewById(R.id.text_nombre)
        var comentario: TextView = itemView.findViewById(R.id.text_contenido)
        var fechaComentario: TextView = itemView.findViewById(R.id.text_fecha)


        fun bind_comments(content_comentario: JSONObject){
            nombreUsuario.text = content_comentario.getString("username")
            fechaComentario.text = content_comentario.getString("comment_date")
            comentario.text = content_comentario.getString("commentary")
        }
    }
}

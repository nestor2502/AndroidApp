package com.unam.lomitos

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * clase que adapta el post a la vista,
 */
class AdaptadorPost {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind_Items(perro:Post) {
            val nombre: TextView = itemView.findViewById(R.id.nombre)
            val likes: TextView = itemView.findViewById(R.id.likes)
            val imagen: ImageView = itemView.findViewById(R.id.foto)

            nombre.text = perro.nombre
            likes.text= perro.likes.toString()
            Glide.with(itemView.context).load(perro.imagen).into(imagen)

            itemView.setOnClickListener {
                startActivity(Intent(this, comments::class.java))
            }
        }
    }
}
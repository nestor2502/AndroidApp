package com.unam.lomitos

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText

class Dialogo(context: Context, interfaz: Comentario, id:String) {
    interface Comentario {
        fun comentar(comentario: String, id:String)
    }

    init {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.setContentView(R.layout.add_comment)
        val comentario = dialog.findViewById(R.id.comentario) as EditText
        val aceptar = dialog.findViewById(R.id.aceptar) as Button
        val cancelar = dialog.findViewById(R.id.cancelar) as Button

        aceptar?.setOnClickListener(View.OnClickListener {
            interfaz.comentar(comentario = comentario.text.toString(),id = id)
            dialog.dismiss()
        })

        cancelar?.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        dialog.show()
    }

}
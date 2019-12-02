package com.unam.lomitos

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.jetbrains.anko.alert

class signup : AppCompatActivity() {
    companion object {
        val LOG_TAG = "Nestor"
    }

    private lateinit var editText3: EditText
    private lateinit var editText4:EditText
    private lateinit var progressBar2: ProgressBar
    private lateinit var button: Button
    val TAG_LOGS = "nestor"
    private var stat = "no hay nada"
    private var key = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        editText3 = findViewById(R.id.editText3)
        editText4 = findViewById(R.id.editText4)
        progressBar2 = findViewById(R.id.progressBar2)
        button = findViewById(R.id.button)


    }
    fun signup(view: View){//accion del boton login
        val username:String = editText3.text.toString() //giving the username of text view
        val password:String = editText4.text.toString() //giving the password of text view
        if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)) {
            val task = Api()
            task.execute("hola")

        }

        else{
            showErrorDialogEmptyField()
        }

    }


    private fun action(){
        //startActivity(Intent(this, connected::class.java))
        val intent = Intent(this, connected::class.java)
        intent.putExtra("key", key)
        startActivity(intent)
    }

    private fun showErrorDialogEmptyField() {
        alert("No puedes dejar campos vacios") {
            yesButton { }
        }.show()
    }
    private fun showErrorConnect() {
        alert("We can't connect") {
            yesButton { }
        }.show()
    }

    private fun showStatus(status: String) {
        alert(status) {
            yesButton { }
        }.show()
    }

    private fun showErrorDialog() {
        alert("this username has been used") {
            yesButton { }
        }.show()
    }

    fun doLogin(username:String, password:String){
        try {

        }
        finally{
            val URL = "http://lomitos-api.tk/login.php?username=$username&password=$password"
            URL.httpGet().responseObject(User.Deserializer()){request, response, result ->
                when (result) {
                    is Result.Success -> {
                        var estado = result.get()
                        key = estado.key
                        Log.i(TAG_LOGS, key)
                        Log.i(TAG_LOGS, "todo se realizo correctamente en AsynkTask funcion externa ")
                    }
                    is Result.Failure -> {
                        Log.i(TAG_LOGS, "algo salio mal en la llamada en AsynkTask funcion externa")
                    }
                }
            }
            Thread.sleep(1000)
            Log.i(TAG_LOGS, "Valor que se retorna: ${key} en AsynkTask funcionexterna")}

    }


    inner class Api : AsyncTask<String, Void, Int>() {

        val username:String = editText3.text.toString() //giving the username of text view
        val password:String = editText4.text.toString()

        override fun onPreExecute() {
            progressBar2.visibility= View.VISIBLE
            button.isClickable = false
            Log.i(TAG_LOGS, "Comienza la llamada AsynkTask")

        }

        override fun doInBackground(vararg params: String?): Int{
            var hola:Int =0
            try { }
            finally{
                val URL = "http://lomitos-api.tk/signup.php?username=$username&password=$password"
                URL.httpGet().responseObject(User.Deserializer()){request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            var estado = result.get()
                            stat = estado.status
                            doLogin(username, password)
                            Log.i(TAG_LOGS, stat)
                            Log.i(TAG_LOGS, "todo se realizo correctamente en AsynkTask ")
                        }
                        is Result.Failure -> {
                            Log.i(TAG_LOGS, "algo salio mal en la llamada en AsynkTask")
                        }
                    }
                }
                Thread.sleep(1000)
                Log.i(TAG_LOGS, "Valor que se retorna: ${stat} en AsynkTask")}

            return hola
        }


        override fun onPostExecute(result: Int?) {
            Log.i(TAG_LOGS, "Resultado que devuelve la funcion getAnswer: ${stat} en AsynkTask post execute")
            if (stat.equals("ok")) {
                Log.i(TAG_LOGS, "Conexion existosa")

                Thread.sleep(1000)
                Log.i(TAG_LOGS, "Valor de la llave despues de obtenerla de LOGIN: ${key}")
                showStatus(key)
                action()

            }
            else if (stat.equals("failed")) {
                Log.i(TAG_LOGS, "Correo o contraseña incorrectos")
                showErrorDialog()

            }
            else{
                Log.i(TAG_LOGS, "No podemos conectarnos")
                showErrorConnect()
            }
            progressBar2.visibility=View.GONE
            button.isClickable = true
            Log.i(TAG_LOGS, "termina la llamada")
            stat = ""

        }

    }

}

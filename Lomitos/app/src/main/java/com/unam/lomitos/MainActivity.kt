package com.unam.lomitos

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import org.jetbrains.anko.alert
import android.util.Log
import android.widget.Button
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result


class MainActivity : AppCompatActivity() {

    companion object {
        val LOG_TAG = "Nestor"
    }

    private lateinit var editText:EditText
    private lateinit var editText2:EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var button: Button
    val TAG_LOGS = "nestor"
    private var stat = "no hay nada"
    private var key:String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        editText2 = findViewById(R.id.editText2)
        progressBar = findViewById(R.id.progressBar)
        button = findViewById(R.id.button)


    }

    fun signup(view: View){//accion del boton sign up
        action()
    }

    fun login(view:View){//accion del boton login
        val username:String = editText.text.toString() //giving the username of text view
        val password:String = editText2.text.toString() //giving the password of text view
        var key = ""
        if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)) {
            val task = Api()
            task.execute("hola")

        }

        else{
            showErrorDialogEmptyField()
        }

    }

    private fun action(){
        startActivity(Intent(this, signup::class.java))
    }
    private fun action2(){
        startActivity(Intent(this, connected::class.java))
    }

    private fun showKey(key: String) {
        alert(key) {
            yesButton { }
        }.show()
    }


    private fun showErrorDialog() {
        alert("usuario o contraseña incorrecto") {
            yesButton { }
        }.show()
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



    inner class Api : AsyncTask<String, Void, Int>() {

    val username:String = editText.text.toString() //giving the username of text view
    val password:String = editText2.text.toString()

    override fun onPreExecute() {
        progressBar.visibility= View.VISIBLE
        button.isClickable = false
        Log.i(TAG_LOGS, "Comienza la llamada AsynkTask")

    }

    override fun doInBackground(vararg params: String?): Int{

        var hola:Int =0
        try {

        }
        finally{
        val URL = "http://lomitos-api.tk/login.php?username=$username&password=$password"
            URL.httpGet().responseObject(User.Deserializer()){request, response, result ->
                when (result) {
                    is Result.Success -> {
                        var estado = result.get()
                        stat = estado.status

                        key = estado.key
                        Log.i(TAG_LOGS, stat)
                        Log.i(TAG_LOGS, key)
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
        Log.i(TAG_LOGS, "Resultado que devuelve la funcion getAnswer: ${stat} en AsynkTask")
        if (stat.equals("ok")) {
            Log.i(TAG_LOGS, "Conexion existosa")
            showKey(key)
            action2()

        }
        else if (stat.equals("failed")) {
            Log.i(TAG_LOGS, "Correo o contraseña incorrectos")
            showErrorDialog()

        }
        else{
            Log.i(TAG_LOGS, "No podemos conectarnos")
            showErrorConnect()
        }
        progressBar.visibility=View.GONE
        button.isClickable = true
        Log.i(TAG_LOGS, "termina la llamada")
        stat = ""
        key = ""

    }

}


}


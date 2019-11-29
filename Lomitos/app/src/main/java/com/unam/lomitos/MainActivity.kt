package com.unam.lomitos

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar

//import para api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.jetbrains.anko.alert
import android.util.Log
import android.widget.Button
import retrofit2.Call
import retrofit2.Callback
import com.google.gson.Gson
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject



class MainActivity : AppCompatActivity() {

    companion object {
        val LOG_TAG = "Nestor"
    }

    lateinit var service: ApiService
    private lateinit var editText:EditText
    private lateinit var editText2:EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var button: Button
    val TAG_LOGS = "nestor"
    private var resultado = "vacio"

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

        //loginUser()
        val username:String = editText.text.toString() //giving the username of text view
        val password:String = editText2.text.toString() //giving the password of text view

        if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
            //progressBar.visibility= View.VISIBLE
            //getKey()
            progressBar.visibility= View.VISIBLE
            doAsync {
                button.isClickable = false
                //getKey()
                Thread.sleep(1000)
                uiThread {
                    progressBar.visibility=View.GONE
                    button.isClickable = true
                    //showMessage(resultado)

                }
            }

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

    fun getKey(){

        val username:String = editText.text.toString() //giving the username of text view
        val password:String = editText2.text.toString() //giving the password of text view
        val queue = Volley.newRequestQueue(this)
        val url = "http://lomitos-api.tk/login.php?"
        var parameter_a = "username="+username
        var parameter_b = "&password="+password
        val request = url+parameter_a+parameter_b




    }

    private fun showErrorDialog() {
        alert("Ha ocurrido un error, inténtelo de nuevo.") {
            yesButton { }
        }.show()
    }
    private fun conn(user: String, password:String) {
        alert(user+password) {
            yesButton { }
        }.show()
    }

    private fun showErrorDialogEmptyField() {
        alert("No puedes dejar campos vacios") {
            yesButton { }
        }.show()
    }

    private fun showMessage(mensaje:String) {
        alert(mensaje) {
            yesButton { }
        }.show()
    }



}


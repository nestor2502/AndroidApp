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



import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

//
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URLEncoder



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


        //sendGetRequest()



    }

    fun signup(view: View){//accion del boton sign up
        action()
    }

    fun login(view:View){//accion del boton login
        val username:String = editText.text.toString() //giving the username of text view
        val password:String = editText2.text.toString() //giving the password of text view
        var llamada = ""
        if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
            try{
            progressBar.visibility= View.VISIBLE
            doAsync {
                button.isClickable = false
                Log.i(TAG_LOGS, "Comienza la llamada")
                getKey(username,password)
                //Thread.sleep(1000)
                uiThread {
                    progressBar.visibility=View.GONE
                    button.isClickable = true
                    Log.i(TAG_LOGS, "termina la llamada")
                    //showMessage(resultado)

                }
            }

        } catch (e: Exception){
                Log.i(TAG_LOGS, "algo salio mal en los hilos")
            }}

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

    private fun getKey(username:String, password: String){
        val url = "http://lomitos-api.tk/login.php?username=$username&password=$password"
        url.httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.i(TAG_LOGS, "algo salio mal en la llamada")
                    }
                    is Result.Success -> {
                        val data = result.get()
                        Log.i(TAG_LOGS, data)
                    }
                }
            }
    }

    private fun ConvertArray(){

    }

    private fun showErrorDialogEmptyField() {
        alert("No puedes dejar campos vacios") {
            yesButton { }
        }.show()
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




}


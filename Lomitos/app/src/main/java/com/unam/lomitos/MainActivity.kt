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
import retrofit2.Response
import android.util.Log
import android.widget.Button
import retrofit2.Call
import retrofit2.Callback
import com.google.gson.Gson
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity() {

    lateinit var service: ApiService
    private lateinit var editText:EditText
    private lateinit var editText2:EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var button: Button
    val TAG_LOGS = "nestor"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        editText2 = findViewById(R.id.editText2)
        progressBar = findViewById(R.id.progressBar)
        button = findViewById(R.id.button)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://lomitos-api.tk/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<ApiService>(ApiService::class.java)


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
                Thread.sleep(5000)
                uiThread {
                    progressBar.visibility=View.GONE
                    button.isClickable = true
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

        var post: User? = null

        service.getKey(username, password).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                //post = response?.body()
                //Log.i(TAG_LOGS, Gson().toJson(post))
                //print(post.toString())
                action2()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                t?.printStackTrace()
            }
        })

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
    /**
    private fun hideKeyboard(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewRoot.windowToken, 0)
    }*/

    private fun showErrorDialogEmptyField() {
        alert("No puedes dejar campos vacios") {
            yesButton { }
        }.show()
    }




}


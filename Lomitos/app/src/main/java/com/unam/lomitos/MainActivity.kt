package com.unam.lomitos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.jetbrains.anko.alert


class MainActivity : AppCompatActivity() {

    lateinit var service: ApiService
    private lateinit var editText:EditText
    private lateinit var editText2:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        editText2 = findViewById(R.id.editText2)

    }

    fun signup(view: View){//accion del boton sign up
        action()
    }

    fun login(view:View){//accion del boton login
        //loginUser()
        val username:String = editText.text.toString() //giving the username of text view
        val password:String = editText2.text.toString() //giving the password of text view
        conn(username, password)
        print("Hola")
    }

    private fun action(){
        startActivity(Intent(this, signup::class.java))
    }
    private fun action2(){
        startActivity(Intent(this, connected::class.java))
    }

    fun loginUser(){
        val username:String = editText.text.toString() //giving the username of text view
        val password:String = editText2.text.toString() //giving the password of text view
        action2()
        /**
        doAsync {
            val call = getRetrofit().create(ApiService::class.java).getKey(username, password).execute()
            val puppies = call.body() as User
            uiThread {
            if (puppies.status == "success") {
                action2()

            } else {
                showErrorDialog()
            }
                hideKeyboard()}
        }*/
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //service = retrofit.create<ApiService>(ApiService::class.java)
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


}


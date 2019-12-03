package com.unam.lomitos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import org.jetbrains.anko.alert
import android.util.Log
import android.widget.Button
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject


class MainActivity : AppCompatActivity() {


    private lateinit var editText:EditText
    private lateinit var editText2:EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var button: Button
    val service = VolleyService()




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
        val url = "http://lomitos-api.tk/login.php?username=$username&password=$password"
        service.initialize(this)
        var key = ""
        var status = ""
            if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)) {
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    status = response["status"].toString()
                    if(status.equals("ok")){
                        key = response["key"].toString()
                        action2(key)
                        progressBar.visibility=View.GONE
                        button.isClickable = true
                        editText.setText("")
                        editText2.setText("")
                        }
                    else if(status.equals("failed")){
                        showErrorDialog()
                    }
                },
                Response.ErrorListener {
                    showErrorConnect()
                })
                service.requestQueue.add(request)
        }
        else{
            showErrorDialogEmptyField()
        }
    }

    private fun action(){
        startActivity(Intent(this, signup::class.java))
    }
    private fun action2(key:String){
        val intent = Intent(this, connected::class.java)
        intent.putExtra("key", key)
        startActivity(intent)
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

}


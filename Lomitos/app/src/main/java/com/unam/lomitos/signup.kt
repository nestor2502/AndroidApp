package com.unam.lomitos

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.jetbrains.anko.alert
import org.json.JSONObject

class signup : AppCompatActivity() {

    private lateinit var editText3: EditText
    private lateinit var editText4:EditText
    private lateinit var progressBar2: ProgressBar
    private lateinit var button: Button
    val service = VolleyService

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
            val url = "http://lomitos-api.tk/signup.php?username=$username&password=$password"
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
                            action(key)
                            progressBar2.visibility=View.GONE
                            button.isClickable = true
                            editText3.setText("")
                            editText4.setText("")
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
        }
        else{
            showErrorDialogEmptyField()
        }
    }

    private fun action(key: String){
        val intent = Intent(this, feed::class.java)
        intent.putExtra("key", key)
        startActivity(intent)
    }

    private fun showErrorDialogEmptyField() {
        alert("No puedes dejar campos vacios") {
            yesButton { }
        }.show()
    }
    private fun showErrorConnect() {
        alert("No se pudo establecer conexión") {
            yesButton { }
        }.show()
    }

    private fun showErrorDialog() {
        alert("El nombre de usuario ya existe") {
            yesButton { }
        }.show()
    }

}

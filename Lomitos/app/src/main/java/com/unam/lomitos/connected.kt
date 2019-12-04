package com.unam.lomitos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.alert

class connected : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connected)

        val intent = intent
        val key = intent.getStringExtra("key")
        showKey(key)
    }

    private fun showKey(status: String) {
        alert(status) {
            yesButton { }
        }.show()
    }
}

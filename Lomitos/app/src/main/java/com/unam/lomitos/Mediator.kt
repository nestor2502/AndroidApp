package com.unam.lomitos

import org.json.JSONObject
import org.json.JSONArray
import android.os.StrictMode
import java.net.PasswordAuthentication

object Mediator {

    private val url = "http://lomitos-api.tk/"
    private  var key= ""

    init {
        if (BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build())

            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build())
        }
    }

    fun login(username:String, password:String){


    }

}
package com.beyondthebrushmobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.beyondthebrushmobile.classes.login_request
import com.beyondthebrushmobile.services.http
import com.beyondthebrushmobile.variables.SERVER_URL
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*
        Get example
        http.get("hi"){
            println(it)
        }
        */


        //Post example
        var json = Gson().toJson(login_request("test"))
        http.post("bye", json){
            println(it)
        }



    }

    fun login(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



}
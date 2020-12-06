package com.beyondthebrushmobile.services

import android.app.Notification
import com.beyondthebrushmobile.variables.SERVER_URL
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object http {

    fun get(path: String, toBeRun : (m: String?) -> Unit){
        val url = SERVER_URL + path
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                toBeRun(body)
            }

            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }
        })
    }

    fun post(path: String, json : String, toBeRun : (m: String?) -> Unit){
        val url = SERVER_URL + path

        val body = json.toRequestBody()
        val request = Request.Builder().url(url).post(body).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                toBeRun(body)
            }

            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }
        })
    }

}
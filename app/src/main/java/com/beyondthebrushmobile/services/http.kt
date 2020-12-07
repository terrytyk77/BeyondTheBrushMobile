package com.beyondthebrushmobile.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beyondthebrushmobile.variables.SERVER_URL
import org.json.JSONObject

object http {

    fun get(context: Context, url: String, toBeRun: (m: String?) -> Unit){
        //Create the request queue
        val requestQueue = Volley.newRequestQueue(context)

        var formatedURL : String

        if(url[0] == '/'){
            formatedURL = "$SERVER_URL$url"
        }else{
            formatedURL = "$SERVER_URL/$url"
        }


        val stringRequest  = StringRequest(
            Request.Method.GET,
            formatedURL,
            { res ->
                toBeRun(res)
            },
            { error ->
                println(error)
            }
        )

        //Adding the Requests to The Request Queue
        requestQueue.add(stringRequest)
    }



    fun post(context: Context, body : JSONObject, url: String, toBeRun: (m: JSONObject?) -> Unit){

        //Create the request queue
        val requestQueue = Volley.newRequestQueue(context)

        var formatedURL : String

        if(url[0] == '/'){
            formatedURL = "$SERVER_URL$url"
        }else{
            formatedURL = "$SERVER_URL/$url"
        }

        val postRequest  = JsonObjectRequest(
            Request.Method.POST,
            formatedURL,
            body,
            { res ->
                toBeRun(res)
            },
            { error ->
                println(error)
            }
        )

        //Adding the Requests to The Request Queue
        requestQueue.add(postRequest)
    }

}



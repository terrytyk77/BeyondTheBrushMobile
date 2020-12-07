package com.beyondthebrushmobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beyondthebrushmobile.fragments.LoginFragment
import com.beyondthebrushmobile.fragments.SignUpFragment
import com.beyondthebrushmobile.variables.SERVER_URL
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //    Test    \\

        //Setup Request Queue
        val requestQueue = Volley.newRequestQueue(this)

        // Get Request
        val stringRequest = JsonObjectRequest(
            Request.Method.GET,
            "$SERVER_URL/hi",
            null,
            { response ->
                println(response.toString())
            },
            { error ->
                println(error)
            }
        )

        // JSON Creation for The Post Request
        val postData = JSONObject()
        try {
            postData.put("name", "Terry")
            postData.put("age", 22)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        // Post Request
        val postRequest  = JsonObjectRequest(
                Request.Method.POST,
                "$SERVER_URL/bye",
                postData,
                { res ->
                    println(res)
                },
                { error ->
                    println(error)
                }
        )

        //Adding the Requests to The Request Queue
        requestQueue.add(stringRequest)
        requestQueue.add(postRequest)

        //------------------------------||


        //Change to the login fragment
        loginFragmentButton.setOnClickListener{
            fragmentManager(LoginFragment())
        }

        //Change to the sign up fragment
        signupFragmentButton.setOnClickListener{
            fragmentManager(SignUpFragment())
        }

        //Set the initial fragment of the layout
        fragmentManager(LoginFragment())

    }

    private fun fragmentManager(fragment: Fragment):Unit{
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.login_fragment_layout, fragment)
            commit()
        }
    }

}
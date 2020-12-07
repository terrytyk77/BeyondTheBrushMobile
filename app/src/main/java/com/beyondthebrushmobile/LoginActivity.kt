package com.beyondthebrushmobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beyondthebrushmobile.fragments.LoginFragment
import com.beyondthebrushmobile.fragments.SignupFragment
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //test
        val username = "terry"
        val password = "secret"
        val age = 22

        val serverUrl = "http://10.0.2.2:3000"

        val stringRequest  = object : StringRequest(
                Request.Method.GET,
                "$serverUrl/hi",
                Response.Listener { res ->
                    println(res)
                },
                Response.ErrorListener { error ->
                    println(error)
                }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                params["age"] = age.toString()
                return super.getParams()
            }
        }

        val postData = JSONObject()
        try {
            postData.put("name", "Terry")
            postData.put("age", 22)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val postRequest  = JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.84:3000/bye",
                postData,
                { res ->
                    println(res)
                },
                { error ->
                    println(error)
                }
        )

        Volley.newRequestQueue(this).add(stringRequest)
        Volley.newRequestQueue(this).add(postRequest)

        //Change to the login fragment
        loginFragmentButton.setOnClickListener{
            fragmentManager(LoginFragment())
        }

        //Change to the sign up fragment
        signupFragmentButton.setOnClickListener{
            fragmentManager(SignupFragment())
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
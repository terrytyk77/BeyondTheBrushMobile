package com.beyondthebrushmobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beyondthebrushmobile.classes.login_request
import com.beyondthebrushmobile.fragments.LoginFragment
import com.beyondthebrushmobile.fragments.SignupFragment
import com.beyondthebrushmobile.services.http
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Change to the login fragment
        loginFragmentButton.setOnClickListener{
            fragmentManager(LoginFragment())
        }

        //Change to the signup fragment
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
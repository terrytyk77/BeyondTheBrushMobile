package com.beyondthebrushmobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beyondthebrushmobile.fragments.LoginFragment
import com.beyondthebrushmobile.fragments.SignUpFragment
import com.beyondthebrushmobile.localStorage.currentUserFiles
import com.beyondthebrushmobile.services.http
import com.beyondthebrushmobile.variables.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_login.signupPassword2
import kotlinx.android.synthetic.main.fragment_login.signupUsername
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    private var currentFrag : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //0 stands for login
        //1 stands for sign_up
        currentFrag = 0;

        fragmentChanger.text = onLoginFrag

        //Add the text click event
        fragmentChanger.setOnClickListener{

            when(currentFrag){
                0 -> {
                    //Change to sign_up
                    fragmentChanger.text = onSignUpFrag
                    fragmentManager(SignUpFragment())
                    currentFrag = 1

                }
                1 -> {
                    //Change to login
                    fragmentChanger.text = onLoginFrag
                    fragmentManager(LoginFragment())
                    currentFrag = 0
                }
            }

        }

        //Set the initial fragment of the layout
        fragmentManager(LoginFragment())

    }


    //Handle the movement between fragments
    private fun fragmentManager(fragment: Fragment):Unit{
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.login_fragment_layout, fragment)
            commit()
        }
    }

    //Handle the user feedback
    private fun userFeedback(message: String?){

        //Just a random element from the login activity for parent reference
        val contextView = findViewById<View>(R.id.logo)

        //The component used for the anchor
        var anchorHolder : View = findViewById(R.id.login_fragment_layout)

        when(currentFrag){
            0 ->{
                //on login
                anchorHolder = findViewById(R.id.loginButton)

            }
            1 ->{
                //on sign_up
                anchorHolder = findViewById(R.id.signupButton)
            }
        }

        if(message != null){
            Snackbar
                    .make(contextView, message, Snackbar.LENGTH_SHORT) //Create the snack bar
                    .setAnchorView(anchorHolder) //Snack bar location
                    .show() //Display the snack bar
        }

    }

    fun loginAttempt(view: View) {
        //Get the data inputs
        val usernameData : String = signupUsername.text.toString()
        val passwordData : String = signupPassword2.text.toString()

        val postData = JSONObject().put("name", usernameData).put("password", passwordData)
        http.post(this, postData, "account/login"){

            //Check the response
            if(it?.getBoolean("status") == true){

                //Locally save the user data
                currentUserFiles.userData = it.getJSONObject("body")
                currentUserFiles.userProfiles = it.getJSONArray("profiles")

                //Create the intent
                val intent = Intent(this, MainActivity::class.java)

                //Send him to the following activity
                startActivity(intent)

            }else{
                //Notify the user about the problem
                userFeedback(it?.getString("result"))
            }

        }
    }

    fun signupAttempt(view: View){

        //Get the data inputs||
            
            //Credentials
            val usernameData : String = signupUsername.text.toString()
            val emailData : String = signupEmail.text.toString()
        
            //passwords
            val passwordData : String = signupPassword.text.toString()
            val passwordData2: String = signupPassword2.text.toString()
        //___________________||

        //Check all the inputs
        if(passwordData != passwordData2){
            userFeedback(DifferentPasswordsError)
        }else if(passwordData.length < 6){
            userFeedback(PasswordShort)
        }else if(emailData.isEmpty() || emailData.length > 30){
            userFeedback(InvalidEmailSize)
        }else if(usernameData.length < 4){
            userFeedback(UsernameTooSmall)
        }else if(usernameData.length > 15){
            userFeedback(UsernameTooBig)
        }else{

            //Contact the server
            val postData = JSONObject().put("name", usernameData).put("password", passwordData).put("email", emailData)

            http.post(this, postData, "account/signup"){

                //Check the response
                if(it?.getBoolean("status") == true){

                    //Create the intent
                    val intent = Intent(this, MainActivity::class.java)

                    //Send him to the following activity
                    startActivity(intent)

                    //Locally save the user data
                    currentUserFiles.userData = it.getJSONObject("body")

                }else{

                    //Notify the user about the problem
                    userFeedback(it?.getString("result"))
                }
            }
        }
    }
}
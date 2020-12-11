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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_login.signupPassword2
import kotlinx.android.synthetic.main.fragment_login.signupUsername
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //0 stands for login
        //1 stands for sign_up
        var currentFrag : Int = 0;
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


    private fun fragmentManager(fragment: Fragment):Unit{
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.login_fragment_layout, fragment)
            commit()
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

                //Create the intent
                val intent = Intent(this, MainActivity::class.java)

                //Send him to the following activity
                startActivity(intent)

                //Locally save the user data
                currentUserFiles.userData = it

            }else{
                //Notify the user about the problem
                Toast.makeText(this, it?.getString("result"), Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, DifferentPasswordsError, Toast.LENGTH_SHORT).show()
        }else if(passwordData.length < 6){
            Toast.makeText(this, PasswordShort, Toast.LENGTH_SHORT).show()
        }else if(emailData.isEmpty() || emailData.length > 30){
            Toast.makeText(this, InvalidEmailSize, Toast.LENGTH_SHORT).show()
        }else if(usernameData.length < 4){
            Toast.makeText(this, UsernameTooSmall, Toast.LENGTH_SHORT).show()
        }else if(usernameData.length > 15){
            Toast.makeText(this, UsernameTooBig, Toast.LENGTH_SHORT).show()
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
                    currentUserFiles.userData = it

                }else{

                    //Notify the user about the problem
                    Toast.makeText(this, it?.getString("result"), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
package com.beyondthebrushmobile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.beyondthebrushmobile.classes.ArmorProfile
import com.beyondthebrushmobile.fragments.ArmorDrawingFragment
import com.beyondthebrushmobile.fragments.MiniGameFragment
import com.beyondthebrushmobile.fragments.TalentTreeFragment
import com.beyondthebrushmobile.localStorage.currentUserFiles
import com.beyondthebrushmobile.services.http
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_armor_drawing.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager(ArmorDrawingFragment())

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_armor_drawing -> fragmentManager(ArmorDrawingFragment())
                R.id.ic_talent_tree -> fragmentManager(TalentTreeFragment())
                R.id.ic_mini_game -> fragmentManager(MiniGameFragment())
            }
            true
        }
    }

    private fun fragmentManager(fragment: Fragment):Unit{
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment_content, fragment)
            commit()
        }
    }

    fun makeNewProfile(view: View, profileArray : MutableList<String>, currentProfileID: Int, toBeRun: (m : Int) -> Unit){
        println("Make a new profile")

        val items = arrayOf("Profile 1", "Profile 2", "Cancel")

        MaterialAlertDialogBuilder(this)
                // Add customization options here
                .setTitle("Choose your template")
                .setItems(items){
                    _, which ->

                    if(which != 2){
                        //Data to send to the server
                        val postData = JSONObject().put("default", which).put("id", currentUserFiles.userData?.getString("_id"))
                        http.post(this, postData, "account/createProfile"){

                            val status : Boolean? = it?.getBoolean("status")

                            if(status != null){
                                if(status){

                                    //Add the profile that was just created
                                    currentUserFiles.userProfiles.put(it?.getJSONObject("body"))

                                    //The profiles was created!
                                    toBeRun(which)
                                    MaterialAlertDialogBuilder(this)
                                            .setTitle(it?.getString("result"))
                                            .setItems(arrayOf("ok")){_,_->}
                                            .show()
                                }else{
                                    MaterialAlertDialogBuilder(this)
                                            .setTitle(it?.getString("result"))
                                            .setItems(arrayOf("ok")){_,_->}
                                            .show()
                                }
                            }

                        }
                    }



                }
                .show()
    }

}
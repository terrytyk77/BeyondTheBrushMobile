package com.beyondthebrushmobile

import android.annotation.SuppressLint
import android.app.Notification
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getFont
import androidx.fragment.app.Fragment
import com.beyondthebrushmobile.classes.NodeCalculation
import com.beyondthebrushmobile.classes.treeInformation
import com.beyondthebrushmobile.fragments.ArmorDrawingFragment
import com.beyondthebrushmobile.fragments.MiniGameFragment
import com.beyondthebrushmobile.fragments.TalentTreeFragment
import com.beyondthebrushmobile.localStorage.currentUserFiles
import com.beyondthebrushmobile.services.http
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_talent_tree.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    lateinit var updateMethod : () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager(ArmorDrawingFragment())

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_armor_drawing -> fragmentManager(ArmorDrawingFragment())
                R.id.ic_talent_tree -> {
                    fragmentManager(TalentTreeFragment())
                }
                R.id.ic_mini_game -> fragmentManager(MiniGameFragment())
            }
            true
        }
    }

    fun giveTheUpdateMethod(action : ()->Unit){
        updateMethod = action
    }

    private fun fragmentManager(fragment: Fragment):Unit{
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment_content, fragment)
            commit()
        }
    }

    fun notification(message : String){
        val popup = MaterialAlertDialogBuilder(this)
            popup
                .setTitle(message)
                .setPositiveButton("ok") { _: DialogInterface, _: Int -> }

        val dialog = popup.create()
        dialog.show()

        val buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        buttonPositive.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    //Clicked on the node
    @SuppressLint("WrongConstant")
    fun selectedTreeNode(view: View){

        val name = view.context.resources.getResourceEntryName(view.id)

        val nodeObject = treeInformation.getNode(name)

        //Show the dialog
        var dialogBOX = MaterialAlertDialogBuilder(this)
            .setMessage(nodeObject.desc)

                if(view.tag == "lockable"){

                    dialogBOX.setTitle("Unlock " + nodeObject.name)
                    dialogBOX.setNegativeButton("Cancel"){dialog, which ->

                    }

                    dialogBOX.setPositiveButton(nodeObject.price.toString() + " resources"){dialog, which ->
                        //Process the purchase
                        val yourBalance = currentUserFiles.userData?.getJSONObject("stats")?.getInt("resources")!!

                        if(yourBalance >= nodeObject.price){
                            //Proccess the purchase
                            //This user has enough money
                            //Handle visuals
                            progressCircle.visibility = View.VISIBLE
                            darkOverlay.visibility = View.VISIBLE

                            //Data to be sent
                            val postBody = JSONObject()
                                    .put("id", currentUserFiles.userData!!.getString("_id"))
                                    .put("tree", currentUserFiles.userData!!.getJSONObject("talentTree"))
                                    .put("node", JSONObject(Gson().toJson(nodeObject)))

                            //Handle the connection to the server
                            http.post(this, postBody, "/save/saveNode"){it->

                                progressCircle.visibility= View.INVISIBLE
                                darkOverlay.visibility = View.INVISIBLE

                                if(it!!.getBoolean("accepted")){

                                    //Update the current files
                                    currentUserFiles.userData!!.put("talentTree", it.getJSONObject("newtree"))
                                    currentUserFiles.userData!!.getJSONObject("stats").put("resources", it.getInt("resources"))

                                    //The node was purchased
                                    notification(it!!.getString("message"))

                                    updateMethod()

                                }else{
                                    //The node wasn't purchased
                                    notification(it!!.getString("message"))
                                }

                            }
                        }else{

                            //In case the user does not have enough money for the purchase
                            val popup = MaterialAlertDialogBuilder(this)
                            popup
                                .setTitle("Not enough resources")
                                .setMessage("You are short by " +  (nodeObject.price - yourBalance).toString() + " resources.")
                                .setPositiveButton("OK"){_,_->}

                            val noMoneyPopUp = popup.create()
                            noMoneyPopUp.show()

                            val buttonPositive = noMoneyPopUp.getButton(DialogInterface.BUTTON_POSITIVE)
                            buttonPositive.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
                            buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.white))
                        }

                    }
                }else if(view.tag == "unavailable"){
                    dialogBOX.setPositiveButton("Ok"){dialog, which ->

                    }
                    dialogBOX.setTitle(nodeObject.name + ": Unavailable")
                }
                else{
                    dialogBOX.setPositiveButton("Ok"){dialog, which ->

                    }
                    dialogBOX.setTitle(nodeObject.name + ": Owned!")
                }

        val dialog = dialogBOX.create()
        dialog.show()

        val buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        buttonPositive.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.white))
    }


    fun makeNewProfile(view: View, profileArray : MutableList<String>, currentProfileID: Int, toBeRun: (m : Int) -> Unit){

        //Create Custom Title
        var title = TextView(this)
        title.text = "Choose your preset"
        title.setPadding(60,60,20,20)
        title.setTextColor(Color.BLACK)
        title.textSize = 20f

        //Create Layout for the Input
        val layoutParams = FrameLayout.LayoutParams(600, 160)
        layoutParams.setMargins(60,0,0,20)

        //Create Custom Input
        val input = EditText(this)
        input.hint = "Profile name..."
        input.setTextColor(Color.BLACK)
        input.textSize = 16f

        //Create Array of Profiles
        val items = arrayOf("Profile 1", "Profile 2")

        val popUpProfile = MaterialAlertDialogBuilder(this)

        popUpProfile
        // Add customization options here
        .setCustomTitle(title)
        .setView(input)
        .setNegativeButton("Cancel", null)
        .setItems(items){
            _, which ->

            //Data to send to the server
            if(input.text.isNotEmpty()){
                val postData = JSONObject().put("default", which).put("id", currentUserFiles.userData?.getString("_id")).put("name", input.text)
                http.post(this, postData, "account/createProfile"){

                    val status : Boolean? = it?.getBoolean("status")

                    if(status != null){
                        if(status){

                            //Add the profile that was just created
                            currentUserFiles.userProfiles.put(it?.getJSONObject("body"))


                            //The profiles was created!
                            toBeRun(which)
                            notification(it.getString("result"))
                        }
                        else{
                            notification(it.getString("result"))
                        }
                    }
                }
            }
            else{
                notification("Failed! Name not given!")
            }
        }

        val popUpProfileDialog = popUpProfile.create()
        popUpProfileDialog.show()

        val buttonNegative = popUpProfileDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        buttonNegative.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.white))

        input.layoutParams = layoutParams
    }

    fun startGame(view: View){
        val intent = Intent(this, MiniGameActivity::class.java)
        startActivity(intent)
    }
}
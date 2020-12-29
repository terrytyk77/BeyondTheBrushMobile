package com.beyondthebrushmobile

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.beyondthebrushmobile.classes.NodeCalculation
import com.beyondthebrushmobile.fragments.ArmorDrawingFragment
import com.beyondthebrushmobile.fragments.MiniGameFragment
import com.beyondthebrushmobile.fragments.TalentTreeFragment
import com.beyondthebrushmobile.localStorage.currentUserFiles
import com.beyondthebrushmobile.services.http
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager(ArmorDrawingFragment())

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_armor_drawing -> fragmentManager(ArmorDrawingFragment())
                R.id.ic_talent_tree -> {
                    updateSlots()
                    fragmentManager(TalentTreeFragment())
                }
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

    fun notification(message : String){
        MaterialAlertDialogBuilder(this)
                .setTitle(message)
                .setItems(arrayOf("ok")){_,_->}
                .show()
    }

    //Clicked on the node
    fun selectedTreeNode(view: View){

        val name = view.context.resources.getResourceEntryName(view.id)
        println(name)
    }

    fun makeNewProfile(view: View, profileArray : MutableList<String>, currentProfileID: Int, toBeRun: (m : Int) -> Unit){

        //Create Custom Title
        var title = TextView(this)
        title.text = "Choose your preset"
        title.setPadding(20,80,20,20)
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.BLACK);
        title.textSize = 20f

        //Create Layout for the Input
        val layoutParams = FrameLayout.LayoutParams(600, 150)
        layoutParams.setMargins(60,0,0,20)

        //Create Custom Input
        val input = EditText(this)
        input.hint = "Profile name..."
        input.setTextColor(Color.BLACK);
        input.textSize = 16f

        //Create Array of Profiles
        val items = arrayOf("Profile 1", "Profile 2")


        MaterialAlertDialogBuilder(this)
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
        .show()
        input.layoutParams = layoutParams
    }


    //Update the talent tree slots
    fun updateSlots(){
        //Store the nodes
        val ownedNodes = mutableListOf<ImageView?>()
        val lockableNodes = mutableListOf<ImageView?>()
        val unavailableNodes = mutableListOf<ImageView?>()

        //Switch function to handle elements
        fun nodeSwitch(elementID : Int){
            val nodeView = this?.findViewById<ImageView>(elementID)
            val nodeName = resources.getResourceEntryName(elementID)

            //Get the correct nodes on the respective places
            when(NodeCalculation.getNodeStatus(nodeName)){
                "type0"->{
                    ownedNodes.add(nodeView)
                }
                "type1"->{
                    lockableNodes.add(nodeView)
                }
                "type2"->{
                    unavailableNodes.add(nodeView)
                }
            }
        }

        //Set the nodes to their according lists
        nodeSwitch(R.id.node0)
        nodeSwitch(R.id.node1)
        nodeSwitch(R.id.node2)
        nodeSwitch(R.id.node3)
        nodeSwitch(R.id.node4)
        nodeSwitch(R.id.node5)
        nodeSwitch(R.id.node6)
        nodeSwitch(R.id.node7)
        nodeSwitch(R.id.node8)
        nodeSwitch(R.id.node9)
        nodeSwitch(R.id.node10)


        //Loop through the owned nodes
        for(node in ownedNodes){
            node?.setColorFilter( Color.parseColor(NodeCalculation.colorOwned) )
        }

        //Loop through the lockable nodes
        for(node in lockableNodes){
            node?.setColorFilter( Color.parseColor(NodeCalculation.colorLockable) )
        }

        //Loop through the unavailable nodes
        for(node in unavailableNodes){
            node?.setColorFilter( Color.parseColor(NodeCalculation.colorUnavailable) )
        }

        //val a = TalentTreeFragment.node0
        //println(a)

        //val a = TalentTreeFragment?.findViewById<ImageView>(R.id.node0)
        //println(a)
        //activity?.findViewById<ImageView>(R.id.node0)?.setColorFilter(Color.parseColor("#66000000"))
    }









}
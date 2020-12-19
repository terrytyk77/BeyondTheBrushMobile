package com.beyondthebrushmobile.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.beyondthebrushmobile.ArmorAdapter
import com.beyondthebrushmobile.DrawingRoomActivity
import com.beyondthebrushmobile.MainActivity
import com.beyondthebrushmobile.R
import com.beyondthebrushmobile.classes.ArmorProfile
import com.beyondthebrushmobile.localStorage.currentUserFiles
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_armor_drawing.*


class ArmorDrawingFragment: Fragment(R.layout.fragment_armor_drawing) {

    //Variables||

        private var armorAdapter:ArmorAdapter?=null
        private val armors = arrayListOf<ArmorProfile>()

        //Current profile IDq
        var currentProfileID : Int = 0;

        //Armor grid view
        lateinit var armorGridView: GridView
    //_________||


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_armor_drawing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)

        val profileArray: MutableList<String> = ArrayList()
        profileArray.add("Profile 1")
        profileArray.add("Profile 2")

        //Add all the profiles the the drop down
        for(i in 0 until currentUserFiles.userProfiles.length()){
            profileArray.add("Profile ${3+i}")
        }

        //Add the create a new profile button
        profileArray.add("New Profile")

        //Update the dropdown
        updateDropdown(view, profileArray)

        //Update the profiles display
        updateCurrentProfile(view, 0)

    }

    fun updateDropdown(view: View, theList : MutableList<String>){

        var profileArray = theList

        //The profiles adapter
        val profileAdapter = ArrayAdapter(
                requireContext(),
                R.layout.profile_drop_down_item,
                profileArray)

        //Getting the view
        val dropdown: AutoCompleteTextView = view.findViewById(R.id.dropdown_text)

        //Default Value
        dropdown.setText(profileArray[currentProfileID])

        //Set Adapter
        dropdown.setAdapter(profileAdapter)

        //When you change a dropdown view
        dropdown.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position: Int, _ ->

            val selectedItem = adapterView.getItemAtPosition(position) as String
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)

            //Reset the current profile ID
            currentProfileID = itemIdAtPos.toInt()

            //Clicked the create new profile
            if(itemIdAtPos.toInt() + 1 == profileArray.size){

                (activity as MainActivity).makeNewProfile(view, profileArray, currentProfileID){

                    //Remake the options list
                    val profileArray2: MutableList<String> = ArrayList()
                    profileArray2.add("Profile 1")
                    profileArray2.add("Profile 2")

                    //Add all the profiles the the drop down
                    for(i in 0 until currentUserFiles.userProfiles.length()){
                        profileArray2.add("Profile ${3+i}")
                    }

                    //Add the create a new profile button
                    profileArray2.add("New Profile")

                    profileArray = profileArray2

                    //Remake the adapter here
                    val profileAdapter2 = ArrayAdapter(
                            requireContext(),
                            R.layout.profile_drop_down_item,
                            profileArray2)

                    dropdown.setText (profileArray[currentProfileID])
                    dropdown.setAdapter(profileAdapter2) //Update with the new adapter
                    updateCurrentProfile(view, itemIdAtPos.toInt())


                }

            }else{
                updateCurrentProfile(view, itemIdAtPos.toInt())
            }

        }

    }

    private fun updateCurrentProfile(view : View, position : Int) {

        when(position){
            0 ->{
                armors.clear()

                //Default 1
                armors.add(ArmorProfile(R.drawable.hat1,"Head"))
                armors.add(ArmorProfile(R.drawable.gloves1,"Gloves"))
                armors.add(ArmorProfile(R.drawable.body_clothes1,"Chest"))
                armors.add(ArmorProfile(R.drawable.boots1,"Boots"))
                armors.add(ArmorProfile(R.drawable.sword1,"Sword"))
                armors.add(ArmorProfile(R.drawable.shiled1,"Shield"))

            }
            1 ->{
                armors.clear()

                //Default 2
                armors.add(ArmorProfile(R.drawable.hat2,"Head"))
                armors.add(ArmorProfile(R.drawable.gloves2,"Gloves"))
                armors.add(ArmorProfile(R.drawable.body_clothes2,"Chest"))
                armors.add(ArmorProfile(R.drawable.boots2,"Boots"))
                armors.add(ArmorProfile(R.drawable.sword2,"Sword"))
                armors.add(ArmorProfile(R.drawable.shield2,"Shield"))
            }
            else->{

                armors.clear()

                val currentItem = currentUserFiles.userProfiles.getJSONObject(position - 2)
                val currentDefault = currentItem.getJSONObject("preset").getInt("name")

                if(currentDefault == 0){
                    armors.add(ArmorProfile(R.drawable.hat1,"Head"))
                    armors.add(ArmorProfile(R.drawable.gloves1,"Gloves"))
                    armors.add(ArmorProfile(R.drawable.body_clothes1,"Chest"))
                    armors.add(ArmorProfile(R.drawable.boots1,"Boots"))
                    armors.add(ArmorProfile(R.drawable.sword1,"Sword"))
                    armors.add(ArmorProfile(R.drawable.shiled1,"Shield"))
                }else if(currentDefault == 1) {
                    armors.add(ArmorProfile(R.drawable.hat2,"Head"))
                    armors.add(ArmorProfile(R.drawable.gloves2,"Gloves"))
                    armors.add(ArmorProfile(R.drawable.body_clothes2,"Chest"))
                    armors.add(ArmorProfile(R.drawable.boots2,"Boots"))
                    armors.add(ArmorProfile(R.drawable.sword2,"Sword"))
                    armors.add(ArmorProfile(R.drawable.shield2,"Shield"))
                }

            }
        }


        armorAdapter = ArmorAdapter(armors, requireActivity())

        if(!this::armorGridView.isInitialized){
            armorGridView = view.findViewById(R.id.armor_grid_view)
        }



        armorGridView.adapter = armorAdapter

        armorGridView.setOnItemClickListener { parent, view, position, id ->
            println("$parent $view $position $id")

            //Store the type
            val armorType = armors[position].armorName

            enterDrawingRoom(view)
        }

    }

   private fun enterDrawingRoom(view: View){
        val intent = Intent(activity, DrawingRoomActivity::class.java)
        startActivity(intent)
   }
}
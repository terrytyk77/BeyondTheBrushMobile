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
import com.beyondthebrushmobile.R
import com.beyondthebrushmobile.classes.ArmorProfile
import com.beyondthebrushmobile.localStorage.currentUserFiles


class ArmorDrawingFragment: Fragment(R.layout.fragment_armor_drawing) {

    //Variables||

        private var armorAdapter:ArmorAdapter?=null
        private val armors = arrayListOf<ArmorProfile>()

        //Current profile IDq
        var currentProfileID : Int = 0;
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
            profileArray.add("Profile $i")
        }

        //Update the dropdown
        updateDropdown(view, profileArray)

        //Update the profiles display
        updateCurrentProfile(view, 0)

    }

    private fun updateDropdown(view: View, profileArray : MutableList<String>){

        //The profiles adapter
        val profileAdapter = ArrayAdapter(
                requireContext(),
                R.layout.profile_drop_down_item,
                profileArray)

        //Getting the view
        val dropdown: AutoCompleteTextView = view.findViewById(R.id.dropdown_text)

        //Default Value
        dropdown.setText(profileArray[0])

        //Set Adapter
        dropdown.setAdapter(profileAdapter)

        //When you change a dropdown view
        dropdown.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position: Int, _ ->

            val selectedItem = adapterView.getItemAtPosition(position) as String
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)

            //Reset the current profile ID
            currentProfileID = itemIdAtPos.toInt()

            //Update the profiles display
            updateCurrentProfile(view, currentProfileID)

            //println("$selectedItem, $itemIdAtPos")
        }

    }

    private fun updateCurrentProfile(view : View, position : Int) {

        // Filling The Armor Array
        armors.add(ArmorProfile(R.drawable.logo,"Head"))
        armors.add(ArmorProfile(R.drawable.logo,"Gloves"))
        armors.add(ArmorProfile(R.drawable.logo,"Chest"))
        armors.add(ArmorProfile(R.drawable.logo,"Boots"))
        armors.add(ArmorProfile(R.drawable.logo,"Sword"))
        armors.add(ArmorProfile(R.drawable.logo,"Shield"))

        armorAdapter = ArmorAdapter(armors, requireActivity())

        val armorGridView: GridView = view.findViewById(R.id.armor_grid_view)
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
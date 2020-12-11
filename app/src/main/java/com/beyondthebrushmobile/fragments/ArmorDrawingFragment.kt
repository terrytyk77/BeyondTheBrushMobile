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
import com.beyondthebrushmobile.LoginActivity
import com.beyondthebrushmobile.R
import com.beyondthebrushmobile.classes.ArmorProfile
import com.beyondthebrushmobile.variables.armors
import kotlinx.android.synthetic.main.fragment_armor_drawing.*


class ArmorDrawingFragment: Fragment(R.layout.fragment_armor_drawing) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_armor_drawing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)

        val profileArray = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")

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

        dropdown.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position: Int, id ->
            val selectedItem = adapterView.getItemAtPosition(position) as String
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)

            println("$selectedItem, $itemIdAtPos")
        }


        var armorAdapter:ArmorAdapter?=null

        // Filling The Armor Array
        armors.add(ArmorProfile(R.drawable.logo,"Head"))
        armors.add(ArmorProfile(R.drawable.logo,"Gloves"))
        armors.add(ArmorProfile(R.drawable.logo,"Chest"))
        armors.add(ArmorProfile(R.drawable.logo,"Boots"))
        armors.add(ArmorProfile(R.drawable.logo,"Sword"))
        armors.add(ArmorProfile(R.drawable.logo,"Shield"))

        armorAdapter = ArmorAdapter(armors, activity!!)

        val armorGridView: GridView = view.findViewById(R.id.armor_grid_view)
        armorGridView.adapter = armorAdapter

        armorGridView.setOnItemClickListener { parent, view, position, id ->
            println("$parent $view $position $id")
            enterDrawingRoom(view)
        }
    }

   private fun enterDrawingRoom(view: View){
        val intent = Intent(activity, DrawingRoomActivity::class.java)
        startActivity(intent)
   }
}
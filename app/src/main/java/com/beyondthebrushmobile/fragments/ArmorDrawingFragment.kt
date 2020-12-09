package com.beyondthebrushmobile.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.beyondthebrushmobile.DrawingRoomActivity
import com.beyondthebrushmobile.LoginActivity
import com.beyondthebrushmobile.R
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

        val adapter = ArrayAdapter(
                context!!,
                R.layout.profile_drop_down_item,
                profileArray)

        val editTextFilledExposedDropdown: AutoCompleteTextView = view.findViewById(R.id.dropdown_text)
        editTextFilledExposedDropdown.setText(profileArray[0])
        editTextFilledExposedDropdown.setAdapter(adapter)
    }
}
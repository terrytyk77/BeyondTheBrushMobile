package com.beyondthebrushmobile.fragments

import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.beyondthebrushmobile.MainActivity
import com.beyondthebrushmobile.R
import com.beyondthebrushmobile.classes.NodeCalculation
import com.beyondthebrushmobile.classes.NodeCalculation.getNodeStatus
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_talent_tree.*

class TalentTreeFragment: Fragment(R.layout.fragment_talent_tree) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {

        view?.findViewById<ImageView>(R.id.node0)?.setColorFilter( Color.parseColor(NodeCalculation.colorUnavailable))


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_talent_tree, container, false)
    }

}
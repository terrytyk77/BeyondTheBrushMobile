package com.beyondthebrushmobile.fragments

import android.graphics.Color
import android.graphics.Insets.add
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.beyondthebrushmobile.MainActivity
import com.beyondthebrushmobile.R
import com.beyondthebrushmobile.classes.ArmorProfile
import com.beyondthebrushmobile.classes.NodeCalculation
import com.beyondthebrushmobile.classes.NodeLocation
import com.beyondthebrushmobile.classes.TreeCanvas
import com.beyondthebrushmobile.localStorage.currentUserFiles
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_talent_tree.*
import org.json.JSONObject
import kotlin.math.absoluteValue


class TalentTreeFragment: Fragment(R.layout.fragment_talent_tree) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_talent_tree, container, false)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            var nodeImages = arrayListOf<NodeLocation>()

            //Adding All the Positions Before Inflating the Views
            nodeImages.add(NodeLocation(node0))
            nodeImages.add(NodeLocation(node1))
            nodeImages.add(NodeLocation(node2))
            nodeImages.add(NodeLocation(node3))
            nodeImages.add(NodeLocation(node4))
            nodeImages.add(NodeLocation(node5))
            nodeImages.add(NodeLocation(node6))
            nodeImages.add(NodeLocation(node7))
            nodeImages.add(NodeLocation(node8))
            nodeImages.add(NodeLocation(node9))
            nodeImages.add(NodeLocation(node10))

            val relativeLayout = rootView.findViewById(R.id.TreeTalent) as RelativeLayout
            relativeLayout.addView(activity?.let { TreeCanvas(it, nodeImages)})

        }, 0)

        return rootView
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).giveTheUpdateMethod(){
            updateSlots()
        }

        updateSlots()
    }

    //Update the talent tree slots
    private fun updateSlots(){
        //Store the nodes
        val ownedNodes = mutableListOf<ImageView?>()
        val lockableNodes = mutableListOf<ImageView?>()
        val unavailableNodes = mutableListOf<ImageView?>()

        var number : JSONObject = JSONObject()
        if(currentUserFiles.userData!!.has("stats")){
            number = currentUserFiles.userData!!.getJSONObject("stats")
        }

        if(number != null){
            resourcesLabel.text = number?.getInt("resources").toString()
        }

        //Switch function to handle elements
        fun nodeSwitch(elementID: Int){
            val nodeView = view?.findViewById<ImageView>(elementID)
            val nodeName = resources.getResourceEntryName(elementID)

            //Get the correct nodes on the respective places
            when(NodeCalculation.getNodeStatus(nodeName)){
                "type0" -> {
                    ownedNodes.add(nodeView)
                }
                "type1" -> {
                    lockableNodes.add(nodeView)
                }
                "type2" -> {
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
            node?.setColorFilter(Color.parseColor(NodeCalculation.colorOwned))
            node?.isClickable = true
            node?.tag = "owned"
        }

        //Loop through the lockable nodes
        for(node in lockableNodes){
            node?.setColorFilter(Color.parseColor(NodeCalculation.colorLockable))
            node?.isClickable = true
            node?.tag = "lockable"
        }

        //Loop through the unavailable nodes
        for(node in unavailableNodes){
            node?.setColorFilter(Color.parseColor(NodeCalculation.colorUnavailable))
            node?.isClickable = false
            node?.tag = "unavailable"
        }

    }
}
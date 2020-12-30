package com.beyondthebrushmobile.fragments

import android.app.Activity
import android.graphics.Color
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_talent_tree, container, false)
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).giveTheUpdateMethod(){
            updateSlots()
        }

        updateSlots()
    }


    //Update the talent tree slots
    fun updateSlots(){
        //Store the nodes
        val ownedNodes = mutableListOf<ImageView?>()
        val lockableNodes = mutableListOf<ImageView?>()
        val unavailableNodes = mutableListOf<ImageView?>()

        //Switch function to handle elements
        fun nodeSwitch(elementID : Int){
            val nodeView = view?.findViewById<ImageView>(elementID)
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
            node?.isClickable = false
        }

        //Loop through the lockable nodes
        for(node in lockableNodes){
            node?.setColorFilter( Color.parseColor(NodeCalculation.colorLockable) )
            node?.isClickable = true
        }

        //Loop through the unavailable nodes
        for(node in unavailableNodes){
            node?.setColorFilter( Color.parseColor(NodeCalculation.colorUnavailable) )
            node?.isClickable = false
        }

    }

}
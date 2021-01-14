package com.beyondthebrushmobile.classes

import android.widget.ImageView
import com.beyondthebrushmobile.R
import com.beyondthebrushmobile.localStorage.currentUserFiles
import org.json.JSONObject

object NodeCalculation {
    //White 0% alpha
    const val colorOwned = "#00FFFFFF"
    //Red 90% alpha
    const val colorBackgroundOwned = "#FF6d0d05"
    //Black 60% alpha
    const val colorLockable = "#99000000"
    //Black 90% alpha
    const val colorUnavailable = "#E6000000"

    fun getNodeStatus(viewName : String?): String {

        var returnMessage: String = "type2"
        var treeObject = currentUserFiles.userData?.getJSONObject("talentTree")
        var treeBoolean : Boolean = false //Has this node
        if(treeObject != null){
            treeBoolean = treeObject.getBoolean(viewName)
        }else{
            currentUserFiles.userData!!.put("talentTree", JSONObject()
                .put("node0", false)
                .put("node1", false)
                .put("node2", false)
                .put("node3", false)
                .put("node4", false)
                .put("node5", false)
                .put("node6", false)
                .put("node7", false)
                .put("node8", false)
                .put("node9", false)
                .put("node10", false)
            )
        }

        if(treeBoolean){
            //Had this node
            return "type0"
        }else{
            when(viewName){
                "node0"->{
                    returnMessage = "type1"
                }
                "node1"->{
                    if(treeObject?.getBoolean("node0")!!){
                        returnMessage = "type1"
                    }
                }
                "node2"->{
                    if(treeObject?.getBoolean("node0")!!){
                        returnMessage = "type1"
                    }
                }
                "node3"->{
                    if(treeObject?.getBoolean("node0")!!){
                        returnMessage = "type1"
                    }
                }
                "node4"->{
                    if(treeObject?.getBoolean("node1")!!){
                        returnMessage = "type1"
                    }
                }
                "node5"->{
                    if(treeObject?.getBoolean("node2")!!){
                        returnMessage = "type1"
                    }
                }
                "node6"->{
                    if(treeObject?.getBoolean("node2")!!){
                        returnMessage = "type1"
                    }
                }
                "node7"->{
                    if(treeObject?.getBoolean("node3")!!){
                        returnMessage = "type1"
                    }
                }
                "node8"->{
                    if(treeObject?.getBoolean("node4")!!){
                        returnMessage = "type1"
                    }
                }
                "node9"->{
                    if(treeObject?.getBoolean("node7")!!){
                        returnMessage = "type1"
                    }
                }
                "node10"->{
                    if(treeObject?.getBoolean("node8")!! || treeObject?.getBoolean("node9")!! || treeObject?.getBoolean("node5")!! || treeObject?.getBoolean("node6")!!){
                        returnMessage = "type1"
                    }
                }

            }
        }


        return returnMessage
    }



}


object treeInformation{

    class nodeData constructor(val id : String, val name : String, val desc : String, val price : Int)


    val nodesTable = mutableListOf<nodeData>(

        //node0
        nodeData(
            "node0",
            "Flash Strike",
            "Basic spell increases your movement speed by 10% for 6 seconds. Not stackable.",
            250
        ),

        //node1
        nodeData(
        "node1",
        "To Arms",
        "The basic spell reduces the cooldown of itself by 0.5 seconds for 6 seconds, stackable up to 1 second.",
        500
        ),

        //node2
        nodeData(
            "node2",
            "Steel Chopper",
            "Xspell is now able to break heavier objects just like stones and iron.",
            500
        ),

        //node3
        nodeData(
            "node3",
            "Shield Block",
            "Get 2 stacks for the defensive spell.",
            500
        ),

        //node4
        nodeData(
            "node4",
            "Battle thirst",
            "Executing an enemy with your Xspell recovers 20% of your HP.",
            750
        ),

        //node5
        nodeData(
            "node5",
            "Greed is Good",
            "You'll get 15% more resources from the dungeon.",
            1250
        ),

        //node6
        nodeData(
            "node6",
            "Gold Digger",
            "The players encounters 25% more chests within the dungeon.",
            1250
        ),

        //node7
        nodeData(
            "node7",
            "Bubble Up",
            "Spawns a bubble blocking the first attack against the player.",
            750
        ),

        //node8
        nodeData(
            "node8",
            "Demand for Action",
            "Every time the player uses 2 consecutive basic attacks, reduce the cooldown of your Xspell by 0.5 seconds.",
            1000
        ),

        //node9
        nodeData(
            "node9",
            "Tough Skin",
            "Reduce debuffs on the player by 25% [Passive]",
            1000
        ),

        //node10
        nodeData(
            "node10",
            "Overkill",
            "Xspell resets after executing an enemy.",
            2500
        )

    )


    fun getNode(nodeID : String) : nodeData{
        for(loopedNode in nodesTable){
            if(loopedNode.id == nodeID){
                return loopedNode
            }
        }
        return nodeData("", "", "", 0)
    }

}
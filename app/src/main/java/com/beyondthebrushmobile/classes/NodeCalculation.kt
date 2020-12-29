package com.beyondthebrushmobile.classes

import android.widget.ImageView
import com.beyondthebrushmobile.R
import com.beyondthebrushmobile.localStorage.currentUserFiles

object NodeCalculation {
    const val colorOwned = "#334CAF50"
    const val colorLockable = "#00000000"
    const val colorUnavailable = "#66000000"

    fun getNodeStatus(viewName : String?): String {

        var returnMessage: String = "type2"
        var treeObject = currentUserFiles.userData?.getJSONObject("talentTree")
        var treeBoolean : Boolean = false //Has this node
        if(treeObject != null){
            treeBoolean = treeObject.getBoolean(viewName)
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
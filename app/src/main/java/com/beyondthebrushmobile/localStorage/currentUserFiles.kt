package com.beyondthebrushmobile.localStorage

import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONObject

object currentUserFiles{

    //Hold the user data
    var userData : JSONObject? = JSONObject()
    var userProfiles : JSONArray = JSONArray()

    //Armor directions
    //0 -> Front
    //1 -> Right
    //2 -> Left
    //3 -> Back
    var currentDirection : Int = 0
    var itemID : String? = ""
    var currentProfileID : Int = 0

}
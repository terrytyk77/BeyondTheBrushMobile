package com.beyondthebrushmobile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.beyondthebrushmobile.classes.ArmorProfile
import com.beyondthebrushmobile.localStorage.currentUserFiles

class ArmorAdapter (var itemList:ArrayList<ArmorProfile>,var context:Context) : BaseAdapter(){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val gridItem = itemList[position]

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itView = inflater.inflate(R.layout.armor_item,null)

        itView.findViewById<ImageView>(R.id.armor_image).setImageResource(gridItem.armorImage)
        itView.findViewById<TextView>(R.id.armor_name).text = gridItem.armorName

        //Look for the image on the profiles
        var base64String : String = currentUserFiles.userProfiles.getJSONObject(currentUserFiles.currentProfileID).getJSONObject("front").getString(gridItem.armorName)
        //Check if there is paint to put on the armor
        if(base64String.isNotEmpty()){

            //Decode the image
            val decodedBase = Base64.decode(base64String, Base64.DEFAULT)
            val decodedBaseByte = BitmapFactory.decodeByteArray(decodedBase, 0, decodedBase.size)

            //Set the image to the UI element
            itView.findViewById<ImageView>(R.id.armor_paint).setImageBitmap(decodedBaseByte)
        }

        return itView

    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }


}
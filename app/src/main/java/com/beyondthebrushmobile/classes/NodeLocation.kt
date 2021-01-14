package com.beyondthebrushmobile.classes

import android.graphics.PointF
import android.widget.ImageView

data class NodeLocation(val image : ImageView){
    fun getMiddle(): PointF {
        return PointF(image.x + image.width/2, image.y + image.height/2)
    }
}

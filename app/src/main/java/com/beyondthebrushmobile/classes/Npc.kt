package com.beyondthebrushmobile.classes

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.beyondthebrushmobile.R

class Npc internal constructor(private val screenX:Int, private val screenY:Int, val res: Resources) {
    private var bullet:Bitmap = BitmapFactory.decodeResource(res, R.drawable.node7)
    internal var width:Int = bullet.width / 4
    internal var height:Int = bullet.height / 4
    internal var x:Int = (0 until screenX - width).random()
    internal var y:Int = - 600


    internal val collisionShape:Rect
        get() {
            return Rect(x, y, x + width, y + height)
        }

    fun drawable(): Bitmap {
//        width = (width * screenRatioX) as Int
//        height = (height * screenRatioY) as Int
        bullet = Bitmap.createScaledBitmap(bullet, width, height, false)
        return bullet
    }

}
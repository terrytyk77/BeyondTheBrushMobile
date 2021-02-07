package com.beyondthebrushmobile.classes

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.beyondthebrushmobile.R


class MiniGameBackground (private val screenX:Int, private val screenY:Int, val res: Resources) {
    internal var x = 0
    internal var y = 0
    internal lateinit var background : Bitmap

    fun drawable(): Bitmap {
        background = BitmapFactory.decodeResource(res, R.drawable.hall)
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false)
        return background
    }
}
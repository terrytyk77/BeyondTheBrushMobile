package com.beyondthebrushmobile.classes

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.beyondthebrushmobile.R

class Npc internal constructor(
        private val screenX: Int,
        private val screenY: Int,
        val res: Resources,
        val hostage : Boolean
) {



    var enemyId = if(hostage){
        R.drawable.hostage
    }else{
        val randomEnemy = (0 until 5).random()
        when(randomEnemy){
            0 -> R.drawable.monster0
            1 -> R.drawable.monster1
            2 -> R.drawable.monster2
            3 -> R.drawable.monster3
            4 -> R.drawable.monster4
            5 -> R.drawable.monster5
            else -> R.drawable.monster0
        }
    }

    private var minSpeed : Int = 150
    private var maxSpeed : Int = 300
    var speed : Int = (minSpeed until maxSpeed).random()


    private var monster:Bitmap = BitmapFactory.decodeResource(res, enemyId)

    internal var width:Int = monster.width / 4
    internal var height:Int = monster.height / 4

    internal var x:Int = (0 until screenX - width).random()
    internal var y:Int = -(0 until screenY).random()

    // *1.5 Because of frame Delay
    fun getCollisionShape():Rect {
        return Rect(x, (y - height/1.5).toInt(), x + width, (y + height/1.5).toInt())
    }

    fun reset(){
        this.x = (0 until screenX - width).random()
        this.y = -(0 until screenY).random()
        this.speed = (minSpeed until maxSpeed).random()
    }

    fun drawable(): Bitmap {
        monster = Bitmap.createScaledBitmap(monster, width, height, false)
        return monster
    }

}
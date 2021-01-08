package com.beyondthebrushmobile.classes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class TreeCanvas @JvmOverloads
constructor(context: Context, var nodeImagesReceived:ArrayList<NodeLocation>, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    //Default Stroke Color
    //The color on which your stroke starts (Default: RED)
    private var defaultColor = Color.WHITE
    private var defaultStroke = 10f

    //Default Brush Initiated
    private var paint = Paint().apply {
        color = defaultColor
        style = Paint.Style.STROKE// default: FILL
        strokeWidth = defaultStroke // default: Hairline-width (really thin)
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
    }

    // Called when the view should render its content.
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        //FlashStrike to ToArms
        canvas?.drawLine(nodeImagesReceived[0].image.x + nodeImagesReceived[0].image.width/2, nodeImagesReceived[0].image.y + nodeImagesReceived[0].image.height/2, nodeImagesReceived[1].image.x + nodeImagesReceived[1].image.width/2, nodeImagesReceived[1].image.y + nodeImagesReceived[1].image.height/2, paint)

        //FlashStrike to SteelChopper
        canvas?.drawLine(nodeImagesReceived[0].image.x + nodeImagesReceived[0].image.width/2, nodeImagesReceived[0].image.y + nodeImagesReceived[0].image.height/2, nodeImagesReceived[2].image.x + nodeImagesReceived[2].image.width/2, nodeImagesReceived[2].image.y + nodeImagesReceived[2].image.height/2, paint)

        //FlashStrike to ShieldBlock
        canvas?.drawLine(nodeImagesReceived[0].image.x + nodeImagesReceived[0].image.width/2, nodeImagesReceived[0].image.y + nodeImagesReceived[0].image.height/2, nodeImagesReceived[3].image.x + nodeImagesReceived[3].image.width/2, nodeImagesReceived[3].image.y + nodeImagesReceived[3].image.height/2, paint)

        //ToArms to BattleThirst
        canvas?.drawLine(nodeImagesReceived[1].image.x + nodeImagesReceived[1].image.width/2, nodeImagesReceived[1].image.y + nodeImagesReceived[1].image.height/2, nodeImagesReceived[4].image.x + nodeImagesReceived[4].image.width/2, nodeImagesReceived[4].image.y + nodeImagesReceived[4].image.height/2, paint)

        //BattleThirst to DemandForAction
        canvas?.drawLine(nodeImagesReceived[4].image.x + nodeImagesReceived[4].image.width/2, nodeImagesReceived[4].image.y + nodeImagesReceived[4].image.height/2, nodeImagesReceived[8].image.x + nodeImagesReceived[8].image.width/2, nodeImagesReceived[8].image.y + nodeImagesReceived[8].image.height/2, paint)

        //DemandForAction to OverKill
        canvas?.drawLine(nodeImagesReceived[8].image.x + nodeImagesReceived[8].image.width/2, nodeImagesReceived[8].image.y + nodeImagesReceived[8].image.height/2, nodeImagesReceived[10].image.x + nodeImagesReceived[10].image.width/2, nodeImagesReceived[10].image.y + nodeImagesReceived[10].image.height/2, paint)

        //ShieldBlock to BubbleUp
        canvas?.drawLine(nodeImagesReceived[3].image.x + nodeImagesReceived[3].image.width/2, nodeImagesReceived[3].image.y + nodeImagesReceived[3].image.height/2, nodeImagesReceived[7].image.x + nodeImagesReceived[7].image.width/2, nodeImagesReceived[7].image.y + nodeImagesReceived[7].image.height/2, paint)

        //BubbleUp to ThoughSkin
        canvas?.drawLine(nodeImagesReceived[7].image.x + nodeImagesReceived[7].image.width/2, nodeImagesReceived[7].image.y + nodeImagesReceived[7].image.height/2, nodeImagesReceived[9].image.x + nodeImagesReceived[9].image.width/2, nodeImagesReceived[9].image.y + nodeImagesReceived[9].image.height/2, paint)

        //ThoughSkin to OverKill
        canvas?.drawLine(nodeImagesReceived[9].image.x + nodeImagesReceived[9].image.width/2, nodeImagesReceived[9].image.y + nodeImagesReceived[9].image.height/2, nodeImagesReceived[10].image.x + nodeImagesReceived[10].image.width/2, nodeImagesReceived[10].image.y + nodeImagesReceived[10].image.height/2, paint)

        //SteelChopper to GreedIsGood
        canvas?.drawLine(nodeImagesReceived[2].image.x + nodeImagesReceived[2].image.width/2, nodeImagesReceived[2].image.y + nodeImagesReceived[2].image.height/2, nodeImagesReceived[5].image.x + nodeImagesReceived[5].image.width/2, nodeImagesReceived[5].image.y + nodeImagesReceived[5].image.height/2, paint)

        //SteelChopper to GoldDigger
        canvas?.drawLine(nodeImagesReceived[2].image.x + nodeImagesReceived[2].image.width/2, nodeImagesReceived[2].image.y + nodeImagesReceived[2].image.height/2, nodeImagesReceived[6].image.x + nodeImagesReceived[6].image.width/2, nodeImagesReceived[6].image.y + nodeImagesReceived[6].image.height/2, paint)

        //GreedIsGood to OverKill
        canvas?.drawLine(nodeImagesReceived[5].image.x + nodeImagesReceived[5].image.width/2, nodeImagesReceived[5].image.y + nodeImagesReceived[5].image.height/2, nodeImagesReceived[10].image.x + nodeImagesReceived[10].image.width/2, nodeImagesReceived[10].image.y + nodeImagesReceived[10].image.height/2, paint)

        //GoldDigger to OverKill
        canvas?.drawLine(nodeImagesReceived[6].image.x + nodeImagesReceived[6].image.width/2, nodeImagesReceived[6].image.y + nodeImagesReceived[6].image.height/2, nodeImagesReceived[10].image.x + nodeImagesReceived[10].image.width/2, nodeImagesReceived[10].image.y + nodeImagesReceived[10].image.height/2, paint)

    }
}
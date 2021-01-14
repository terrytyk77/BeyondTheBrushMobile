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
        canvas?.drawLine(nodeImagesReceived[0].getMiddle().x, nodeImagesReceived[0].getMiddle().y, nodeImagesReceived[1].getMiddle().x, nodeImagesReceived[1].getMiddle().y, paint)

        //FlashStrike to SteelChopper
        canvas?.drawLine(nodeImagesReceived[0].getMiddle().x, nodeImagesReceived[0].getMiddle().y, nodeImagesReceived[2].getMiddle().x, nodeImagesReceived[2].getMiddle().y, paint)

        //FlashStrike to ShieldBlock
        canvas?.drawLine(nodeImagesReceived[0].getMiddle().x, nodeImagesReceived[0].getMiddle().y, nodeImagesReceived[3].getMiddle().x, nodeImagesReceived[3].getMiddle().y, paint)

        //ToArms to BattleThirst
        canvas?.drawLine(nodeImagesReceived[1].getMiddle().x, nodeImagesReceived[1].getMiddle().y, nodeImagesReceived[4].getMiddle().x, nodeImagesReceived[4].getMiddle().y, paint)

        //BattleThirst to DemandForAction
        canvas?.drawLine(nodeImagesReceived[4].getMiddle().x, nodeImagesReceived[4].getMiddle().y, nodeImagesReceived[8].getMiddle().x, nodeImagesReceived[8].getMiddle().y, paint)

        //DemandForAction to OverKill
        canvas?.drawLine(nodeImagesReceived[8].getMiddle().x, nodeImagesReceived[8].getMiddle().y, nodeImagesReceived[10].getMiddle().x, nodeImagesReceived[10].getMiddle().y, paint)

        //ShieldBlock to BubbleUp
        canvas?.drawLine(nodeImagesReceived[3].getMiddle().x, nodeImagesReceived[3].getMiddle().y, nodeImagesReceived[7].getMiddle().x, nodeImagesReceived[7].getMiddle().y, paint)

        //BubbleUp to ThoughSkin
        canvas?.drawLine(nodeImagesReceived[7].getMiddle().x, nodeImagesReceived[7].getMiddle().y, nodeImagesReceived[9].getMiddle().x, nodeImagesReceived[9].getMiddle().y, paint)

        //ThoughSkin to OverKill
        canvas?.drawLine(nodeImagesReceived[9].getMiddle().x, nodeImagesReceived[9].getMiddle().y, nodeImagesReceived[10].getMiddle().x, nodeImagesReceived[10].getMiddle().y, paint)

        //SteelChopper to GreedIsGood
        canvas?.drawLine(nodeImagesReceived[2].getMiddle().x, nodeImagesReceived[2].getMiddle().y, nodeImagesReceived[5].getMiddle().x, nodeImagesReceived[5].getMiddle().y, paint)

        //SteelChopper to GoldDigger
        canvas?.drawLine(nodeImagesReceived[2].getMiddle().x, nodeImagesReceived[2].getMiddle().y, nodeImagesReceived[6].getMiddle().x, nodeImagesReceived[6].getMiddle().y, paint)

        //GreedIsGood to OverKill
        canvas?.drawLine(nodeImagesReceived[5].getMiddle().x, nodeImagesReceived[5].getMiddle().y, nodeImagesReceived[10].getMiddle().x, nodeImagesReceived[10].getMiddle().y, paint)

        //GoldDigger to OverKill
        canvas?.drawLine(nodeImagesReceived[6].getMiddle().x, nodeImagesReceived[6].getMiddle().y, nodeImagesReceived[10].getMiddle().x, nodeImagesReceived[10].getMiddle().y, paint)

    }
}
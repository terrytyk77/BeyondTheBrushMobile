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

        val nodeMiddle = arrayListOf<PointF>()

        nodeImagesReceived.forEach{ node ->
            nodeMiddle.add(node.getMiddle())
        }

        //FlashStrike to ToArms
        canvas?.drawLine(nodeMiddle[0].x, nodeMiddle[0].y, nodeMiddle[1].x, nodeMiddle[1].y, paint)

        //FlashStrike to SteelChopper
        canvas?.drawLine(nodeMiddle[0].x, nodeMiddle[0].y, nodeMiddle[2].x, nodeMiddle[2].y, paint)

        //FlashStrike to ShieldBlock
        canvas?.drawLine(nodeMiddle[0].x, nodeMiddle[0].y, nodeMiddle[3].x, nodeMiddle[3].y, paint)

        //ToArms to BattleThirst
        canvas?.drawLine(nodeMiddle[1].x, nodeMiddle[1].y, nodeMiddle[4].x, nodeMiddle[4].y, paint)

        //BattleThirst to DemandForAction
        canvas?.drawLine(nodeMiddle[4].x, nodeMiddle[4].y, nodeMiddle[8].x, nodeMiddle[8].y, paint)

        //DemandForAction to OverKill
        canvas?.drawLine(nodeMiddle[8].x, nodeMiddle[8].y, nodeMiddle[10].x, nodeMiddle[10].y, paint)

        //ShieldBlock to BubbleUp
        canvas?.drawLine(nodeMiddle[3].x, nodeMiddle[3].y, nodeMiddle[7].x, nodeMiddle[7].y, paint)

        //BubbleUp to ThoughSkin
        canvas?.drawLine(nodeMiddle[7].x, nodeMiddle[7].y, nodeMiddle[9].x, nodeMiddle[9].y, paint)

        //ThoughSkin to OverKill
        canvas?.drawLine(nodeMiddle[9].x, nodeMiddle[9].y, nodeMiddle[10].x, nodeMiddle[10].y, paint)

        //SteelChopper to GreedIsGood
        canvas?.drawLine(nodeMiddle[2].x, nodeMiddle[2].y, nodeMiddle[5].x, nodeMiddle[5].y, paint)

        //SteelChopper to GoldDigger
        canvas?.drawLine(nodeMiddle[2].x, nodeMiddle[2].y, nodeMiddle[6].x, nodeMiddle[6].y, paint)

        //GreedIsGood to OverKill
        canvas?.drawLine(nodeMiddle[5].x, nodeMiddle[5].y, nodeMiddle[10].x, nodeMiddle[10].y, paint)

        //GoldDigger to OverKill
        canvas?.drawLine(nodeMiddle[6].x, nodeMiddle[6].y, nodeMiddle[10].x, nodeMiddle[10].y, paint)

    }
}
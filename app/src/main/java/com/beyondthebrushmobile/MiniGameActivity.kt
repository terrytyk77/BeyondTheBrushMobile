package com.beyondthebrushmobile

import android.app.Activity
import android.graphics.*
import android.os.Bundle
import android.util.TypedValue
import android.view.View


class MiniGameActivity : Activity(){

    private val size = Point()
    private var gameView : GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        //To get the size of the action bar
        val tv = TypedValue()
        var actionBarHeight = 0
        if (this.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                tv.data,
                resources.displayMetrics
            )
        }

        //Screen Size
        size.x = resources.displayMetrics.widthPixels
        size.y = resources.displayMetrics.heightPixels + actionBarHeight

        //Inflate the Surface View
        gameView = GameView(this, size.x, size.y)
        setContentView(gameView)
    }

    override fun onPause() {
        super.onPause()
        gameView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        gameView?.onResume()
    }
}
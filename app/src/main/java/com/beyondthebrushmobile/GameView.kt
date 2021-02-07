package com.beyondthebrushmobile

import android.annotation.SuppressLint
import android.graphics.*
import android.os.CountDownTimer
import android.view.SurfaceView
import com.beyondthebrushmobile.classes.MiniGameBackground
import com.beyondthebrushmobile.classes.Npc


@SuppressLint("ViewConstructor")
class GameView(activity: MiniGameActivity, screenX:Int, screenY:Int) : SurfaceView(activity), Runnable{

    private var enemyKilledCounter : Int = 0
    private var gameStartTimer : Long = 3000
    private var gameStartCounter: Int = 3
    private var gameStartTimerDone: Boolean = false
    private var thread : Thread? = null
    private var isPlaying : Boolean = true
    private  var paint: Paint = Paint()
    private  var counterPaint: Paint = Paint()

    private lateinit var npcs:ArrayList<Npc>
    private val size = Point(screenX, screenY)

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            sleep()
        }
    }

    fun onResume() {
        //Initialize Thread
        thread = Thread(this)
        thread!!.start()

        //Paint Option
        paint.textSize = 128f
        paint.color = Color.WHITE
        paint.isAntiAlias = true

        //Paint Option for Start Counter
        counterPaint.textSize = 300f
        counterPaint.color = Color.WHITE
        counterPaint.textAlign = Paint.Align.CENTER
        counterPaint.isAntiAlias = true

        //NPC Initialization
        npcs = ArrayList(4)
        for (i in 0..3)
        {
            npcs.add(Npc(size.x, size.y, resources))
        }

        //Start Game
        isPlaying = true

        //Start Countdown
        object: CountDownTimer(gameStartTimer, 1000) {
            override fun onTick(millisUntilFinished:Long) {
                gameStartTimer - millisUntilFinished
                gameStartCounter--
            }
            override fun onFinish() {
                gameStartTimerDone = true
            }
        }.start()
    }

    fun onPause() {
        try
        {
            isPlaying = false
            thread?.join()
        }
        catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun sleep() {
        try
        {
            Thread.sleep(16)
        }
        catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private  fun update(){
        //The Update Loop     ||
            if(gameStartCounter <= 0) {
                npcs.forEach {
                    it.y += 200
                }
            }
        //--------------------||
    }

    private fun draw(){

        if (holder.surface.isValid) {
            //Initialize the Canvas and Background
            val canvas = holder.lockCanvas()
            canvas.drawBitmap(
                MiniGameBackground(size.x, size.y, this.resources).drawable(),
                0f,
                0f,
                paint
            )

            //The Draw Loop     ||
                if (gameStartCounter <= 0) {
                    //Game Started
                    npcs.forEach {
                        canvas.drawBitmap(it.drawable(), it.x.toFloat(), it.y.toFloat(), paint)
                    }

                    canvas.drawText("score", 50f, 120f, paint)
                }
                else{
                    //Before Game Start
                    canvas.drawText("${gameStartCounter+1}", size.x/2.toFloat(), size.y/2.toFloat(), counterPaint)
                }
            //------------------||

            //Put the Canvas Out!! It has to be the last line!
            holder.unlockCanvasAndPost(canvas)
        }
    }
}
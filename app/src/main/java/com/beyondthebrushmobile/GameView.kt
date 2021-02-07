package com.beyondthebrushmobile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.SurfaceView
import com.beyondthebrushmobile.classes.MiniGameBackground
import com.beyondthebrushmobile.classes.Npc

@SuppressLint("ViewConstructor")
class GameView(private val activity: MiniGameActivity, screenX:Int, screenY:Int) : SurfaceView(activity), Runnable{

    //States
    private var isPlaying : Boolean = true
    private var isGameOver : Boolean = false
    private var gameStartTimerDone: Boolean = false
    private var clickToExit: Boolean = false

    //Thread
    private var thread : Thread? = null

    //Timers
    private var enemyKilledCounter : Int = 0
    private var gameStartTimer : Long = 3000
    private var gameStartCounter: Int = 3

    //Brushes
    private  var paint: Paint = Paint()
    private  var counterPaint: Paint = Paint()
    private  var gameOverPaint: Paint = Paint()

    private lateinit var npcs:ArrayList<Npc>
    private val size = Point(screenX, screenY)

    //Thread Calling Every Frame
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
        paint.setShadowLayer(10f,0f,0f,Color.BLACK)
        paint.color = Color.WHITE
        paint.isAntiAlias = true

        //Paint Option for Start Counter
        counterPaint.textSize = 300f
        counterPaint.color = Color.WHITE
        counterPaint.setShadowLayer(10f,0f,0f,Color.BLACK)
        counterPaint.textAlign = Paint.Align.CENTER
        counterPaint.isAntiAlias = true

        //Paint Option for Game Over
        gameOverPaint.textSize = 180f
        gameOverPaint.color = Color.WHITE
        gameOverPaint.setShadowLayer(10f,0f,0f,Color.RED)
        gameOverPaint.textAlign = Paint.Align.CENTER
        gameOverPaint.isAntiAlias = true

        //NPC Initialization
        npcs = ArrayList(6)
        for (i in 0..5)
        {
            npcs.add(Npc(size.x, size.y, resources, false))
        }

        //Hostage Initialization
        npcs.add(Npc(size.x, size.y, resources, true))

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
            Thread.sleep(10)
        }
        catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private  fun update(){
        //The Update Loop     ||
            if(gameStartTimerDone) {
                npcs.forEach {
                    it.y += it.speed

                    if(it.y > size.y){
                       it.reset()
                    }

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
            if (gameStartTimerDone) {
                //Game Started
                if (isPlaying) {
                    npcs.forEach {
                        canvas.drawBitmap(it.drawable(), it.x.toFloat(), it.y.toFloat(), paint)
                    }
                }
                else {
                    canvas.drawText("Game Over", size.x/2.toFloat(), size.y/2.toFloat(), gameOverPaint)
                }
                //Score Counter Text
                canvas.drawText("score: $enemyKilledCounter", 50f, 150f, paint)
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent):Boolean {
        if(event.action == MotionEvent.ACTION_DOWN) {
            if(isGameOver){
                exit()
                clickToExit = true
            }
            else{
                val clickPos = Rect(event.x.toInt(),event.y.toInt(), event.x.toInt(), event.y.toInt())
                npcs.forEach {
                    if (Rect.intersects(it.getCollisionShape(), clickPos))
                    {
                        if(it.hostage){
                            isPlaying = false
                            isGameOver = true
                        }
                        else{
                            it.reset()
                            enemyKilledCounter++
                        }
                    }
                }
            }

        }
        return true
    }

    private fun exit(){
        try
        {
            if(!clickToExit){
                Thread.sleep(1000)
                activity.startActivity(Intent(activity, MainActivity::class.java))
                activity.finishAffinity()
            }
        }
        catch (e:InterruptedException) {
            e.printStackTrace()
        }
    }
}
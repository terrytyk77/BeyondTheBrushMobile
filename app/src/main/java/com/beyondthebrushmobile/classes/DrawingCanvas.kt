package com.beyondthebrushmobile.classes

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.Toast
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.model.IntegerHSLColor
import com.beyondthebrushmobile.DrawingRoomActivity
import com.beyondthebrushmobile.MainActivity
import com.beyondthebrushmobile.localStorage.currentUserFiles
import com.beyondthebrushmobile.variables.defaultStrokeSize
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import kotlin.math.abs


class DrawingCanvas @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr)  {

    //The canvas element
    private lateinit var extraCanvas: Canvas

    //All the drawings in bitmap||

        //1 -> front
        //2 -> right
        //3 -> back
        //4 -> left

    private lateinit var extraBitmap: Bitmap
    private lateinit var extraBitmap1: Bitmap
    private lateinit var extraBitmap2: Bitmap
    private lateinit var extraBitmap3: Bitmap
    private lateinit var extraBitmap4: Bitmap
    //__________________________||

    //The bounds of the canvas
    private lateinit var frame: Rect

    //Finger tracking variables||
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private var currentX = 0f
    private var currentY = 0f
    //_________________________||

    //The path map||
        //used for the undo/redo functionality
        private var path = Path()
        private var pathArray = LinkedHashMap<Path, Paint>()
        private val pathArrayUndone = LinkedHashMap<Path, Paint>()
    //____________||

    //Get finger strength
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    //Default Background Color
    //Set to transparent in order to work in PNG
    private val backgroundColor = Color.TRANSPARENT

    //Default Stroke Color
    //The color on which your stroke starts (Default: RED)
    var defaultColor: IntegerHSLColor = colorRGB(255, 0, 0)

    //Default Brush Initiated
    private var paint = createBrush()

    // Called when the view should render its content.
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        println("Drawing uwu")

        //Choose correct bitmap
        canvas?.drawBitmap(extraBitmap, 0f, 0f, null)

        //Canvas borders
        canvas?.drawRect(frame, createBrush(newColor = colorRGB(255, 255, 255)))

    }

    fun reDrawCanvas(lastDirection: Int){

        println(lastDirection.toString() + "/" + currentUserFiles.currentDirection)

        //Store the last state
        when(lastDirection){
            0->{
                extraBitmap1 = extraBitmap
            }
            1->{
                extraBitmap2 = extraBitmap
            }
            2->{
                extraBitmap3 = extraBitmap
            }
            3->{
                extraBitmap4 = extraBitmap
            }
        }

        //Set the current bitmap
        when(currentUserFiles.currentDirection){
            0->{
                extraBitmap = extraBitmap1
            }
            1->{
                extraBitmap = extraBitmap2
            }
            2->{
                extraBitmap = extraBitmap3
            }
            3->{
                extraBitmap = extraBitmap4
            }
        }

        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
        this.invalidate()
    }

    //In case the window size changes
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        //Display bitmap
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        //Bitmap 1
        if (::extraBitmap1.isInitialized) extraBitmap1.recycle()
        extraBitmap1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        //Bitmap 2
        if (::extraBitmap2.isInitialized) extraBitmap2.recycle()
        extraBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        //Bitmap 3
        if (::extraBitmap3.isInitialized) extraBitmap3.recycle()
        extraBitmap3 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        //Bitmap 4
        if (::extraBitmap4.isInitialized) extraBitmap4.recycle()
        extraBitmap4 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)



        // Calculate a rectangular frame around the picture.
        val inset = 30
        frame = Rect(inset, inset, width - inset, height - inset)

    }

    //While the finger slides around the canvas
    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
        }
        return true
    }

    //When the finger touches the screen
    private fun touchStart() {
        pathArrayUndone.clear()
        path = Path()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {

            /* QuadTo() adds a quadratic bezier from the last point,
            approaching control point (x1,y1), and ending at (x2,y2). */
            path.quadTo(
                    currentX,
                    currentY,
                    (motionTouchEventX + currentX) / 2,
                    (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY

            // Draw the path in the extra bitmap to cache it.
            extraCanvas.drawPath(path, paint)
            pathArray[path] = paint
        }
        invalidate()
    }

    //Make the brush
    private fun createBrush(
            newColor: IntegerHSLColor = defaultColor,
            newStrokeSize: Float = defaultStrokeSize,
            newStyle: Paint.Style = Paint.Style.STROKE
    ): Paint{
        return Paint().apply {
            color = newColor.toColorInt()
            style = newStyle // default: FILL
            strokeWidth = newStrokeSize // default: Hairline-width (really thin)
            // Smooths out edges of what is drawn without affecting shape.
            isAntiAlias = true
            // Dithering affects how colors with higher-precision than the device are down-sampled.
            isDither = true
            strokeJoin = Paint.Join.ROUND // default: MITER
            strokeCap = Paint.Cap.ROUND // default: BUTT
        }
    }

    private fun colorRGB(r: Int, g: Int, b: Int) : IntegerHSLColor{
        return IntegerHSLColor().also {
            it.setFromColorInt(
                    Color.rgb(
                            r,
                            g,
                            b
                    )
            )
        }
    }

    // Public Functions that can be used in DrawingActivity ||

        fun changeStrokeSize(newSize: Float){
            paint = createBrush(
                    newStrokeSize = newSize, newColor = colorRGB(
                    paint.color.red,
                    paint.color.green,
                    paint.color.blue
                )
            )
        }

        fun changeBrushColor(color: IntegerHSLColor){
            paint = createBrush(newColor = color, newStrokeSize = paint.strokeWidth)
        }

        fun undo(){
            if (pathArray.isNotEmpty()){
                canvasClear()
                pathArrayUndone[pathArray.keys.last()] = pathArray.values.last()
                pathArray.remove(pathArray.keys.last())

                pathArray.forEach { (path, paint) ->
                    extraCanvas.drawPath(path, paint)
                }
                invalidate()
            }else{
                //To Be Replaced By A Toasted
                println("Can't Undo More!")
            }

        }

        fun redo(){
            if (pathArrayUndone.isNotEmpty()){
                canvasClear()
                pathArray[pathArrayUndone.keys.last()] = pathArrayUndone.values.last()
                pathArrayUndone.remove(pathArray.keys.last())

                pathArray.forEach { (path, paint) ->
                    extraCanvas.drawPath(path, paint)
                }
                invalidate()
            }else{
                //To Be Replaced By A Toasted
                println("Can't Redo More!")
            }

        }

        private fun canvasClear(){
            extraBitmap.eraseColor(backgroundColor)
        }

        fun canvasReset(){
            canvasClear()
            pathArray.clear()
            pathArrayUndone.clear()
        }

        fun createAnImage(view: Context, toBeRun: (m: JSONObject) -> Unit){

            println("Created the image")

            //Conversion||
                var baos = ByteArrayOutputStream()
                extraBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                var imageBytes = baos.toByteArray()
                var encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            //__________||

            println(encodedImage)

            //val postData = JSONObject().put("image", encodedImage)

            //Close the stream
            baos.close()

            //Call the function
            //toBeRun(postData)
        }

    //------------------------------------------------------||
}
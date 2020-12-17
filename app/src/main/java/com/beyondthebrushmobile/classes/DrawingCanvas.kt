package com.beyondthebrushmobile.classes

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.model.IntegerHSLColor
import com.beyondthebrushmobile.R
import com.beyondthebrushmobile.services.http
import com.beyondthebrushmobile.variables.defaultStrokeSize
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import kotlin.math.abs


class DrawingCanvas @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr)  {

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private lateinit var frame: Rect

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    private var currentX = 0f
    private var currentY = 0f

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var path = Path()

    //Default Background Color
    private val backgroundColor = Color.TRANSPARENT

    //Default Stroke Color
    var defaultColor: IntegerHSLColor = colorRGB(255, 0, 0)

    //Default Brush Initiated
    private var paint = createBrush()


    // Called when the view should render its content.
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(extraBitmap, 0f, 0f, null)
        //canvas?.drawRect(frame, createBrush(newColor = colorRGB(255, 255, 255)))
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        //extraCanvas.drawColor(backgroundColor)

        // Calculate a rectangular frame around the picture.
        val inset = 30
        frame = Rect(inset, inset, width - inset, height - inset)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y
        println(event.action)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun touchStart() {
        path.reset()
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
        }
        invalidate()
    }

    private fun touchUp() {
        // Reset the path so it doesn't get drawn again.
        path.reset()
    }

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

        fun canvasReset(){
            extraBitmap.eraseColor(backgroundColor);
        }

        fun createAnImage(view : Context, toBeRun : (m: JSONObject) -> Unit){

            //Conversion||
                var baos = ByteArrayOutputStream()
                extraBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                var imageBytes = baos.toByteArray()
                var encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            //__________||

            val postData = JSONObject().put("image", encodedImage)

            //Close the stream
            baos.close()

            //Call the function
            toBeRun(postData)
        }

    //------------------------------------------------------||
}
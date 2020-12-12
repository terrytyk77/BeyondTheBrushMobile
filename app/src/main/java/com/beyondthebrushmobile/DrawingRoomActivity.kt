package com.beyondthebrushmobile


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.beyondthebrushmobile.classes.DrawingCanvas
import com.beyondthebrushmobile.variables.initialStrokeSize
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_drawing_room.*



class DrawingRoomActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_room)

        // Link to our Canvas
        val myCanvasView = this.findViewById(R.id.drawingCanvas) as DrawingCanvas


        //Moved the color slider
        colorSlider.setOnColorChangeListener { colorBarPosition, alphaBarPosition, color ->
            //Send the data to the canvas
            myCanvasView.changeBrushColor(color)
        }

        // Set the Slider Initial Position
        strokeSizeSlider.value = initialStrokeSize

        //Slider Position
        strokeSizeSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Responds to when slider's touch event is being started
            }

            override fun onStopTrackingTouch(slider: Slider) {
                myCanvasView.changeStrokeSize(slider.value)
            }
        })
    }
}
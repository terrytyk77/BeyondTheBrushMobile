package com.beyondthebrushmobile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import com.beyondthebrushmobile.classes.DrawingCanvas
import com.beyondthebrushmobile.services.http
import com.beyondthebrushmobile.variables.defaultStrokeSize
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_drawing_room.*


class DrawingRoomActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_room)

        // Link to our Canvas
        val myCanvasView = this.findViewById(R.id.drawingCanvas) as DrawingCanvas


        // Group pickers with PickerGroup to automatically synchronize color across them
        val group = PickerGroup<IntegerHSLColor>().also {
            it.registerPickers(
                colorSlider,
                lightSlider,
                alphaSlider
            )
        }

        // Listen individual pickers or groups for changes
        group.addListener(
            object : HSLColorPickerSeekBar.DefaultOnColorPickListener() {
                override fun onColorChanged(picker: ColorSeekBar<IntegerHSLColor>, color: IntegerHSLColor, value: Int) {
                    myCanvasView.changeBrushColor(color)
                }
            }
        )


        // Set the Slider Initial Position
        strokeSizeSlider.value = defaultStrokeSize

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

    fun SaveImage(view : View){
        drawingCanvas.createAnImage(this){
            http.post(this, it, "/test"){
                println(it?.getString("result"))
            }
        }
    }

}
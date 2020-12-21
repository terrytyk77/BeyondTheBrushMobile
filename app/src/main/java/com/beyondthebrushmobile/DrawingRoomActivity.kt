package com.beyondthebrushmobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import com.beyondthebrushmobile.classes.DrawingCanvas
import com.beyondthebrushmobile.localStorage.currentUserFiles
import com.beyondthebrushmobile.services.http
import com.beyondthebrushmobile.variables.ARMOR_URL
import com.beyondthebrushmobile.variables.Armor_ID
import com.beyondthebrushmobile.variables.Profile_ID
import com.beyondthebrushmobile.variables.defaultStrokeSize
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_drawing_room.*
import org.json.JSONObject


class DrawingRoomActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_room)

        //Change to the correct item portrait
        changeToCorrectItem(intent)

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

        // Reset Button Listener
        Undo.setOnClickListener {
            myCanvasView.undo()
        }

        Redo.setOnClickListener {
            myCanvasView.redo()
        }

        Reset.setOnClickListener {
            myCanvasView.canvasReset()
        }


        fun updateImage(currentDirection : Int){

            val presetID : Int = currentUserFiles.userProfiles.getJSONObject(currentUserFiles.currentProfileID).getJSONObject("preset").getInt("name")

            //Check the current item
            when(currentUserFiles.itemID){
                "Head"->{
                    when(currentDirection){
                        0->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.hat1)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.hat2)
                                }
                            }

                        }
                        1->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.hat1_r)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.hat2_r)
                                }
                            }
                        }
                        2->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.hat1_b)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.hat2_b)
                                }
                            }
                        }
                        3->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.hat1_l)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.hat2_l)
                                }
                            }
                        }
                    }
                }
                "Gloves"->{
                    when(currentDirection){
                        0->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.gloves1_f)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.gloves2_f)
                                }
                            }

                        }
                        1->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.gloves1_f)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.gloves2_f)
                                }
                            }
                        }
                        2->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.gloves1_b)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.gloves2_b)
                                }
                            }
                        }
                        3->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.gloves1_b)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.gloves2_b)
                                }
                            }
                        }
                    }
                }
                "Chest"->{
                    when(currentDirection){
                        0->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.body_clothes1)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.body_clothes2)
                                }
                            }

                        }
                        1->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.body_clothes1_r)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.body_clothes2_r)
                                }
                            }
                        }
                        2->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.body_clothes1_b)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.body_clothes2_b)
                                }
                            }
                        }
                        3->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.body_clothes1_l)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.body_clothes2_l)
                                }
                            }
                        }
                    }
                }
                "Boots"->{
                    when(currentDirection){
                        0->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.shoes1)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.shoes2)
                                }
                            }

                        }
                        1->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.shoes1_r)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.shoes2_r)
                                }
                            }
                        }
                        2->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.shoes1_b)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.shoes2_b)
                                }
                            }
                        }
                        3->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.shoes1_l)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.shoes2_l)
                                }
                            }
                        }
                    }
                }
                "Sword"->{
                    when(presetID){
                        0->{
                            itemPortrait.setImageResource(R.drawable.sword1)
                        }
                        1->{
                            itemPortrait.setImageResource(R.drawable.sword2)
                        }
                    }
                }
                "Shield"->{
                    when(currentDirection){
                        0->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.shield1)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.shield2)
                                }
                            }

                        }
                        1->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.shield1_b)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.shield2_b)
                                }
                            }
                        }
                        2->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.shield1_b)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.shield2_b)
                                }
                            }
                        }
                        3->{
                            when(presetID){
                                0->{
                                    itemPortrait.setImageResource(R.drawable.shield1_b)
                                }
                                1->{
                                    itemPortrait.setImageResource(R.drawable.shield2_b)
                                }
                            }
                        }
                    }
                }

            }
        }

        //Set the turning buttons of the item
        //These buttons are used to change the direction of the items that are being displayed
        //pressing right will add 1 to the value
        //while pressing left will reduce 1 of the value
        moveToTheRight.setOnClickListener{

            val lastPosition = currentUserFiles.currentDirection

            if(currentUserFiles.currentDirection >= 3){
                currentUserFiles.currentDirection = 0
            }else{
                currentUserFiles.currentDirection++
            }

            when(currentUserFiles.currentDirection){
                0->{
                    currentSideText.text = "Front"
                }
                1->{
                    currentSideText.text = "Right"
                }
                2->{
                    currentSideText.text = "Back"
                }
                3->{
                    currentSideText.text = "Left"
                }
            }

            updateImage(currentUserFiles.currentDirection)

            drawingCanvas.reDrawCanvas(lastPosition)

        }

        moveToTheLeft.setOnClickListener{

            val lastPosition = currentUserFiles.currentDirection

            if(currentUserFiles.currentDirection <= 0){
                currentUserFiles.currentDirection = 3
            }else{
                currentUserFiles.currentDirection--
            }

            when(currentUserFiles.currentDirection){
                0->{
                    currentSideText.text = "Front"
                }
                1->{
                    currentSideText.text = "Right"
                }
                2->{
                    currentSideText.text = "Back"
                }
                3->{
                    currentSideText.text = "Left"
                }
            }

            updateImage(currentUserFiles.currentDirection)

            drawingCanvas.reDrawCanvas(lastPosition)

        }

    }

    private fun changeToCorrectItem(intent : Intent){

        //Get the intent values
        val itemID = intent.getStringExtra(Armor_ID)
        var currentProfile = intent.getIntExtra(Profile_ID, 0)

        currentUserFiles.currentDirection = 0
        currentSideText.text = "Front"

        //Save the profile and items IDs
        currentUserFiles.currentProfileID = currentProfile - 2
        currentUserFiles.itemID = itemID

        //Get the profile element
        val profile : JSONObject = currentUserFiles.userProfiles.get(currentProfile - 2) as JSONObject

        //Get the correct preset
        //0 is profile 1
        //1 is profile 2
        val presetID : Int = profile.getJSONObject("preset").getInt("name")

        //Set the correct image
        when(itemID){
            "Head" ->{
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.hat1)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.hat2)
                }

                val helmetImage = getItem(profile, "front", "head")

                if(helmetImage.length > 10){
                    //Then there is data to be loaded
                }

            }
            "Gloves"->{
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.gloves1_f)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.gloves2_f)
                }

                val glovesImage = getItem(profile, "front", "Gloves")

                if(glovesImage.length > 10){
                    //Then there is data to be loaded
                }
            }
            "Chest"->{
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.body_clothes1)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.body_clothes2)
                }

                val chestImage = getItem(profile, "front", "Chest")

                if(chestImage.length > 10){
                    //Then there is data to be loaded
                }
            }
            "Boots"->{
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.shoes1)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.shoes2)
                }

                val bootsImage = getItem(profile, "front", "Boots")

                if(bootsImage.length > 10){
                    //Then there is data to be loaded
                }
            }
            "Sword"-> {
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.sword1)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.sword2)
                }

                val swordImage = getItem(profile, "front", "Sword")

                if(swordImage.length > 10){
                    //Then there is data to be loaded
                }
            }
            "Shield"->{
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.shield1)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.shield2)
                }

                val shieldImage = getItem(profile, "front", "Shield")

                if(shieldImage.length > 10){
                    //Then there is data to be loaded
                }
            }
        }

        //Display the drawing if there is already one
        println(profile.getJSONObject("front").getString("head").length)

    }

    private fun getItem(profile: JSONObject, side: String, piece: String) : String{
        return profile.getJSONObject(side).getString(piece)
    }

    fun closeDrawing(view: View){

        //Move to the main activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun saveImage(view : View){
        drawingCanvas.createAnImage(this){

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            http.post(this, it, ARMOR_URL){
                //Print the response from the server
                println(it?.getString("result"))
            }
        }
    }

}
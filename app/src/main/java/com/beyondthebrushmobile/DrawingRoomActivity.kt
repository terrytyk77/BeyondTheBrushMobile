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
                val helmetImage2 = getItem(profile, "right", "head")
                val helmetImage3 = getItem(profile, "back", "head")
                val helmetImage4 = getItem(profile, "left", "head")


                if(helmetImage.length > 10 && helmetImage2.length > 10 && helmetImage3.length > 10 && helmetImage4.length > 10){
                    //Then there is data to be loaded
                    drawingCanvas.setupDrawing(helmetImage, helmetImage2, helmetImage3, helmetImage4)
                }

            }
            "Gloves"->{
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.gloves1_f)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.gloves2_f)
                }

                val glovesImage1 = getItem(profile, "front", "Gloves")
                val glovesImage2 = getItem(profile, "right", "Gloves")
                val glovesImage3 = getItem(profile, "back", "Gloves")
                val glovesImage4 = getItem(profile, "left", "Gloves")

                if(glovesImage1.length > 10){
                    //Then there is data to be loaded
                    drawingCanvas.setupDrawing(glovesImage1, glovesImage2, glovesImage3, glovesImage4)
                }
            }
            "Chest"->{
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.body_clothes1)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.body_clothes2)
                }

                val chestImage1 = getItem(profile, "front", "Chest")
                val chestImage2 = getItem(profile, "right", "Chest")
                val chestImage3 = getItem(profile, "back", "Chest")
                val chestImage4 = getItem(profile, "left", "Chest")


                if(chestImage1.length > 10){
                    //Then there is data to be loaded
                    drawingCanvas.setupDrawing(chestImage1, chestImage2, chestImage3, chestImage4)
                }
            }
            "Boots"->{
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.shoes1)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.shoes2)
                }

                val bootsImage1 = getItem(profile, "front", "Boots")
                val bootsImage2 = getItem(profile, "right", "Boots")
                val bootsImage3 = getItem(profile, "back", "Boots")
                val bootsImage4 = getItem(profile, "left", "Boots")


                if(bootsImage1.length > 10){
                    //Then there is data to be loaded
                    drawingCanvas.setupDrawing(bootsImage1, bootsImage2, bootsImage3, bootsImage4)
                }
            }
            "Sword"-> {
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.sword1)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.sword2)
                }

                val swordImage1 = getItem(profile, "front", "Sword")
                val swordImage2 = getItem(profile, "right", "Sword")
                val swordImage3 = getItem(profile, "back", "Sword")
                val swordImage4 = getItem(profile, "left", "Sword")

                if(swordImage1.length > 10){
                    //Then there is data to be loaded
                    drawingCanvas.setupDrawing(swordImage1, swordImage2, swordImage3, swordImage4)
                }
            }
            "Shield"->{
                if(presetID == 0){
                    itemPortrait.setImageResource(R.drawable.shield1)
                }else if(presetID == 1){
                    itemPortrait.setImageResource(R.drawable.shield2)
                }

                val shieldImage1 = getItem(profile, "front", "Shield")
                val shieldImage2 = getItem(profile, "right", "Shield")
                val shieldImage3 = getItem(profile, "back", "Shield")
                val shieldImage4 = getItem(profile, "left", "Shield")

                if(shieldImage1.length > 10){
                    //Then there is data to be loaded
                    drawingCanvas.setupDrawing(shieldImage1, shieldImage2, shieldImage3, shieldImage4)
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
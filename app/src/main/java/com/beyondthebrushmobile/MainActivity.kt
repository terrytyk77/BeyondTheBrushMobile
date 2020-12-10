package com.beyondthebrushmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.beyondthebrushmobile.fragments.ArmorDrawingFragment
import com.beyondthebrushmobile.fragments.MiniGameFragment
import com.beyondthebrushmobile.fragments.TalentTreeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_armor_drawing.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager(ArmorDrawingFragment())

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_armor_drawing -> fragmentManager(ArmorDrawingFragment())
                R.id.ic_talent_tree -> fragmentManager(TalentTreeFragment())
                R.id.ic_mini_game -> fragmentManager(MiniGameFragment())
            }
            true
        }
    }

    private fun fragmentManager(fragment: Fragment):Unit{
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment_content, fragment)
            commit()
        }
    }

    fun enterDrawingRoom(view: View){

//        if(view.id != null){
//            println((view.id.toString())
//        }
        when (view.id){
//            R.id.imageView1 -> println("image1")
//            R.id.imageView2 -> println("image2")
//            R.id.imageView3 -> println("image3")
//            R.id.imageView4 -> println("image4")
//            R.id.imageView5 -> println("image5")
//            R.id.imageView6 -> println("image6")
        }

        val intent = Intent(this, DrawingRoomActivity::class.java)
        startActivity(intent)
    }

}
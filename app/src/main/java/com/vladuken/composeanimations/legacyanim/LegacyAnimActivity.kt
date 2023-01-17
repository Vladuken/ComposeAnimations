package com.vladuken.composeanimations.legacyanim

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vladuken.composeanimations.R

class LegacyAnimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legacy_anim)

        val bigSmall = findViewById<Button>(R.id.button_big_small)
        val leftRight = findViewById<Button>(R.id.button_left_right)

        bigSmall.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BigSmallFragment())
                .addToBackStack(null)
                .commit()
        }

        leftRight.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LeftRightFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
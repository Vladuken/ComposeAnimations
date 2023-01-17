package com.vladuken.composeanimations.performance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class ComposePerfActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManyAnimations()
        }
    }
}
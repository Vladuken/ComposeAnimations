package com.vladuken.composeanimations.motion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import com.vladuken.composeanimations.R

// 1. Motion Scene
//      a) start
//      b) end
//      c) transition
// 2. Progress

@Composable
@Preview
@ExperimentalMotionApi
fun MotionLayoutDemo() {
    Column(modifier = Modifier.fillMaxSize()) {
        var progress by remember { mutableStateOf(0f) }
        val context = LocalContext.current
        val scene =
            remember {
                context
                    .resources
                    .openRawResource(R.raw.motion_scene)
                    .readBytes()
                    .decodeToString()
            }

        Slider(progress, { progress = it })

        MotionLayout(
            motionScene = MotionScene(content = scene),
            progress = progress,
            modifier = Modifier
                .fillMaxSize()
        ) {

            val props = motionProperties(id = "box")
            val radius = props.value.int("radius")

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = radius.dp))
                    .background(color = Color.DarkGray)
                    .layoutId("box")
            ) {

                Text(text = radius.toString())
            }
        }
    }

}
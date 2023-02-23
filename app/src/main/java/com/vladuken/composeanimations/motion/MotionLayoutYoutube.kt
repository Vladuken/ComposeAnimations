package com.vladuken.composeanimations.motion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun MotionLayoutYoutube() {
    Column(modifier = Modifier.fillMaxSize()) {
        var progress by remember { mutableStateOf(0f) }
        val context = LocalContext.current
        val scene =
            remember {
                context
                    .resources
                    .openRawResource(R.raw.motion_scene_youtube)
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
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .layoutId("video")
            )
            Box(
                modifier = Modifier
                    .background(Color.Yellow)
                    .layoutId("right_controls")
                    .padding(8.dp)
            ) {
                Text(
                    text = "Music video",
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 1
                )
            }

            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .layoutId("text_under_video")
                    .padding(16.dp)
            ) {

                Text(
                    text = "Music video",
                    color = Color.White,
                    fontSize = 30.sp
                )
            }
        }
    }

}
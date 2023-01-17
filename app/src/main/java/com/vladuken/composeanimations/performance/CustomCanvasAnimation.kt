package com.vladuken.composeanimations.performance

import androidx.compose.animation.VectorConverter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
@Preview
fun CustomCanvasAnimation() {

    val angle = remember { Animatable(0f) }
    val color = remember {
        Animatable(Color.Green, Color.VectorConverter(ColorSpaces.LinearSrgb))
    }

    LaunchedEffect(angle, color) {
        launch {
            while (true) {
                angle.animateTo(360f, animationSpec = tween(3000))
                angle.snapTo(0f)
            }
        }
        launch {
            var index = 0
            while (true) {
                val need = index % colors.size
                color.animateTo(colors[need], animationSpec = tween(3000))
                ++index
            }
        }
    }
    Canvas(modifier = Modifier.fillMaxSize(),
        onDraw = {
            rotate(angle.value) {

                val rectSize = size.height * 0.7f

                drawRoundRect(
                    color = color.value,
                    cornerRadius = CornerRadius(16.dp.toPx()),
                    topLeft = Offset(size.height / 2 - rectSize / 2, size.width / 2 - rectSize / 2),
                    size = Size(rectSize, rectSize)
                )
            }
        }
    )
}

@Preview
@Composable
fun ManyAnimations() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        for (i in 0 until ROWS) {
            Row {
                for (j in 0 until COLS) {
                    Box(Modifier.size(BOX_SIZE_DP.dp)) {
                        CustomCanvasAnimation()
                    }
                }
            }
        }
    }

}
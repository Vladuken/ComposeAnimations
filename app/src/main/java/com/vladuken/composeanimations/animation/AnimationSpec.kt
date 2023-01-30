package com.vladuken.composeanimations.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun AnimationSpecScreen() {
    Column(
        modifier = Modifier.padding(
            horizontal = 32.dp,
            vertical = 64.dp
        )
    ) {
        var isEnabled by remember { mutableStateOf(false) }
        val fraction = remember(isEnabled) { if (isEnabled) 1f else 0f }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                isEnabled = !isEnabled
            }) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null
                )
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                ),
                text = if (isEnabled) "0 -> 1" else "0 <- 1",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        val duration = 1500
        // Tween
        AnimationSpecExample(
            fraction = fraction,
            animationSpec = tween(duration)
        )
        AnimationSpecExample(
            fraction = fraction,
            animationSpec = tween(duration, easing = LinearEasing)
        )
//
//        AnimationSpecExample(
//            fraction = fraction,
//            animationSpec = tween(
//                durationMillis = duration,
//                easing = LinearEasing
//            )
//        )
//
//        AnimationSpecExample(
//            fraction = fraction,
//            animationSpec = tween(
//                durationMillis = duration,
//                easing = FastOutSlowInEasing
//            )
//        )
//
//        AnimationSpecExample(
//            fraction = fraction,
//            animationSpec = tween(
//                durationMillis = duration,
//                easing = CubicBezierEasing(0.61f, -0.79f, 0.73f, 1.59f)
//            )
//        )
//
//        Spacer(
//            modifier = Modifier
//                .height(16.dp)
//                .background(Color.Black)
//        )
//        // Spring
//        val reallySlowStiffness = 15f
//        AnimationSpecExample(
//            fraction = fraction,
//            animationSpec = spring(
//                stiffness = reallySlowStiffness
//            )
//        )
//
//        AnimationSpecExample(
//            fraction = fraction,
//            animationSpec = spring(
//                dampingRatio = Spring.DampingRatioHighBouncy,
//                stiffness = reallySlowStiffness
//            )
//        )
//
//        //keyframes
//        AnimationSpecExample(
//            fraction = fraction,
//            animationSpec = keyframes {
//                val durationStep = 10
//                durationMillis = durationStep * 100
//                0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
//                0.4f at durationStep * 10 // ms
//                0.6f at durationStep * 35 // ms
//                0.2f at durationStep * 60 // ms
//                0.6f at durationStep * 80 // ms
//            }
//        )
//        // Repeatable
//        AnimationSpecExample(
//            fraction = fraction,
//            animationSpec = repeatable(
//                iterations = 5,
//                animation = tween(1000)
//            )
//        )
//
//        // TODO Сделать что-то с infiniteRepeatable
//        AnimationSpecExample(
//            fraction = fraction,
//            animationSpec = infiniteRepeatable(
//                animation = tween(1000),
//                repeatMode = RepeatMode.Restart
//            )
//        )
//
//        // Snap
//        AnimationSpecExample(
//            fraction = fraction,
//            animationSpec = snap(
//                delayMillis = 1000
//            )
//        )
    }
}

@Composable
private fun AnimationSpecExample(
    modifier: Modifier = Modifier,
    fraction: Float,
    animationSpec: AnimationSpec<Float> = tween(1000),
) {
    val fraction: Float by animateFloatAsState(
        targetValue = fraction,
        animationSpec = animationSpec,
    )
    Spacer(modifier = Modifier.height(8.dp))

    Column {
        AnimationDebugString(
            modifier = modifier,
            fraction = fraction,
        )
        if (animationSpec is TweenSpec) {
            EasingGraph(
                easing = animationSpec.easing,
                currentFraction = fraction
            )
        }
    }

}

@Composable
fun EasingGraph(
    modifier: Modifier = Modifier,
    easing: Easing,
    currentFraction: Float
) {
    val path by remember { mutableStateOf(Path()) }
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f)
    ) {
        // Draw Axis
        path.reset()
        path.lineTo(0f, size.height)
        path.lineTo(size.width, size.height)
        drawPath(
            path = path,
            color = Color.LightGray,
            style = Stroke(width = 2f),
        )

        // Draw function
        path.reset()
        path.moveTo(0f, size.height)
        generateSequence(0f) { it + 0.01f }
            .takeWhile { it <= currentFraction }
            .forEach {
                path.lineTo(size.width * it, size.height - size.height * easing.transform(it))
            }

        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 3f),
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0x00_FF_FF_FF
)
@Composable
fun AnimationSpecScreenPreview() {
    AnimationSpecScreen()
}

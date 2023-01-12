package com.vladuken.composeanimations.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun AnimationSpecScreen() {
    Column {
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


        val duration = 1000
        // Tween
        AnimationSpecExample(
            fraction = fraction,
            animationSpec = tween(duration)
        )
        AnimationSpecExample(
            fraction = fraction,
            animationSpec = tween(duration, 100)
        )

        AnimationSpecExample(
            fraction = fraction,
            animationSpec = tween(
                durationMillis = duration,
                easing = LinearEasing
            )
        )

        AnimationSpecExample(
            fraction = fraction,
            animationSpec = tween(
                durationMillis = duration,
                easing = FastOutSlowInEasing
            )
        )

        AnimationSpecExample(
            fraction = fraction,
            animationSpec = tween(
                durationMillis = duration,
                easing = CubicBezierEasing(0.61f, -0.79f, 0.73f, 1.59f)
            )
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
                .background(Color.Black)
        )
        // Spring
        val reallySlowStiffness = 15f
        AnimationSpecExample(
            fraction = fraction,
            animationSpec = spring(
                stiffness = reallySlowStiffness
            )
        )

        AnimationSpecExample(
            fraction = fraction,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = reallySlowStiffness
            )
        )

        //keyframes
        AnimationSpecExample(
            fraction = fraction,
            animationSpec = keyframes {
                val durationStep = 10
                durationMillis = durationStep * 100
                0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
                0.4f at durationStep * 10 // ms
                0.6f at durationStep * 35 // ms
                0.2f at durationStep * 60 // ms
                0.6f at durationStep * 80 // ms
            }
        )
        // Repeatable
        AnimationSpecExample(
            fraction = fraction,
            animationSpec = repeatable(
                iterations = 5,
                animation = tween(1000)
            )
        )

        // TODO Сделать что-то с infiniteRepeatable
        AnimationSpecExample(
            fraction = fraction,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Restart
            )
        )

        // Snap
        AnimationSpecExample(
            fraction = fraction,
            animationSpec = snap(
                delayMillis = 1000
            )
        )
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
    AnimationDebugString(
        modifier = modifier,
        fraction = fraction,
        text = "Current Fraction: $fraction"
    )
}

@Preview(showBackground = true)
@Composable
fun AnimationSpecScreenPreview() {
    AnimationSpecScreen()
}

package com.vladuken.composeanimations.animation

import androidx.compose.animation.core.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vladuken.composeanimations.core.EasingGraph


@Composable
fun AnimationSpecScreen() {
    Column(
        modifier = Modifier.padding(
            horizontal = 32.dp,
            vertical = 64.dp
        )
    ) {
        var isEnabled by remember { mutableStateOf(false) }

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
        val fraction = remember(isEnabled) { if (isEnabled) 1f else 0f }
        ExampleWithGraphAndEasing(
            duration = duration,
            fraction = fraction
        )

//
//        AnimationSpecExample(
//            fraction = fraction,
//            animationSpec = tween(
//                durationMillis = duration,
//                easing = CubicBezierEasing(0.61f, -0.79f, 0.73f, 1.59f)
//            )
//        )

//        Spacer(
//            modifier = Modifier
//                .height(16.dp)
//                .background(Color.Black)
//        )
        SpringExamples(fraction)

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
private fun SpringExamples(fraction: Float) {
    val noBouncy by createAnimation(
        fraction = fraction,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )
    val lowBouncy by createAnimation(
        fraction = fraction,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )
    val mediumBouncy by createAnimation(
        fraction = fraction,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )
    val highBouncy by createAnimation(
        fraction = fraction,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )
    Column {
        val baseFraction = 0.72f
        AnimationSpecExample(
            fraction = noBouncy,
            baseFraction = baseFraction
        )
        AnimationSpecExample(
            fraction = lowBouncy,
            baseFraction = baseFraction
        )
        AnimationSpecExample(
            fraction = mediumBouncy,
            baseFraction = baseFraction
        )
        AnimationSpecExample(
            fraction = highBouncy,
            baseFraction = baseFraction
        )
    }
}

@Composable
private fun ExampleWithGraphAndEasing(duration: Int, fraction: Float) {
    val colors: List<Color> = listOf(
        Color.Blue,
        Color.Red,
        Color.Cyan
    )

    val tweens = listOf(
        tween(duration, easing = FastOutSlowInEasing),
        tween<Float>(duration, easing = LinearEasing),
    )

    tweens.forEachIndexed { index, tweenSpec ->
        val animatedFraction by createAnimation(
            fraction = fraction,
            animationSpec = tweenSpec
        )
        AnimationSpecExample(
            fraction = animatedFraction,
            pinColor = colors[index]
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    val graphFraction by createAnimation(
        fraction = fraction,
        animationSpec = tween(duration, easing = LinearEasing)
    )
    EasingGraph(
        currentFraction = graphFraction,
        easings = tweens.map { it.easing },
        colors = colors
    )
}

@Composable
private fun createAnimation(
    fraction: Float,
    animationSpec: AnimationSpec<Float>,
): State<Float> {
    return animateFloatAsState(
        targetValue = fraction,
        animationSpec = animationSpec,
    )
}

@Composable
fun AnimationSpecExample(
    modifier: Modifier = Modifier,
    fraction: Float,
    pinColor: Color = Color.DarkGray,
    baseFraction: Float = 1f
) {
    AnimationDebugString(
        modifier = modifier,
        fraction = fraction,
        pinColor = pinColor,
        baserFraction = baseFraction
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0x00_FF_FF_FF
)
@Composable
fun AnimationSpecScreenPreview() {
    AnimationSpecScreen()
}

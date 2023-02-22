package com.vladuken.composeanimations.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vladuken.composeanimations.core.EasingGraph


@Composable
fun AnimationSpecScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 24.dp,
                vertical = 32.dp
            )
    ) {
        var isEnabled by remember { mutableStateOf(false) }

        ToggleButtonWithArrow(isEnabled) { isEnabled = !isEnabled }
        Spacer(modifier = Modifier.height(16.dp))
        val fraction = remember(isEnabled) { if (isEnabled) 1f else 0f }

        GroupHeader("Easing")
        GraphAndEasingExample(fraction)

        GroupHeader("Spring")
        SpringExamples(fraction)

        GroupHeader("KeyFrames")
        KeyFrameExample(fraction)

        GroupHeader("Repeatable")
        RepeatableExample(fraction)

        GroupHeader("Snap")
        SnapExample(fraction)
    }
}

@Composable
private fun GroupHeader(heading: String) {
    Text(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        text = heading,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun SnapExample(fraction: Float) {
    val snap by createAnimation(
        fraction = fraction,
        animationSpec = snap(delayMillis = 0)
    )
    AnimationSpecExample(
        fraction = snap,
    )
}

@Composable
private fun ToggleButtonWithArrow(
    isEnabled: Boolean,
    toggle: () -> Unit
) {
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = toggle) {
                Icon(
                    imageVector = if (isEnabled) {
                        Icons.Default.PlayArrow
                    } else {
                        Icons.Default.PlayArrow
                    },
                    contentDescription = null
                )
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "0",
                    style = MaterialTheme.typography.headlineMedium
                )
                Icon(
                    imageVector = if (isEnabled) {
                        Icons.Default.ArrowForward
                    } else {
                        Icons.Default.ArrowBack
                    },
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "1",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }

}

@Composable
private fun RepeatableExample(fraction: Float) {
    val repeatableAnimation by createAnimation(
        fraction = fraction, animationSpec = repeatable(
            iterations = 3,
            animation = tween(1000)
        )
    )

    val infiniteRepeatable by createAnimation(
        fraction = fraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )
    // Repeatable
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "repeatable",
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.width(8.dp))
        AnimationSpecExample(
            modifier = Modifier.weight(2f),
            fraction = repeatableAnimation,
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "infiniteRepeatable",
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.width(8.dp))
        AnimationSpecExample(
            modifier = Modifier.weight(2f),
            fraction = infiniteRepeatable,
        )
    }
}

@Composable
private fun KeyFrameExample(fraction: Float) {
    val keyframeAnimations by createAnimation(
        fraction = fraction,
        animationSpec = keyframes {
            durationMillis = 2000
            0.0f atFraction 0f with LinearOutSlowInEasing // for 0-15 ms
            0.2f atFraction 0.25f with FastOutLinearInEasing // for 15-75 ms
            0.4f atFraction 0.5f // ms
            0.4f atFraction 0.7f // ms
        }
    )
    AnimationSpecExample(
        fraction = keyframeAnimations,
    )
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
        val baseFraction = 1f
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
private fun GraphAndEasingExample(fraction: Float) {
    val duration = 1500

    val colors: List<Color> = listOf(
        Color.Blue,
        Color.Red,
        Color.Green,
        Color.Magenta,
        Color.Cyan
    )

    val tweenSpecs: List<TweenSpec<Float>> = listOf(
        tween(duration, easing = FastOutSlowInEasing),
        tween(duration, easing = LinearEasing),
        tween(duration, easing = FastOutLinearInEasing),
        tween(duration, easing = LinearOutSlowInEasing),
//        tween<Float>(duration, easing = CubicBezierEasing(0.61f, -0.79f, 0.73f, 1.59f)),
    )

    tweenSpecs.forEachIndexed { index, tweenSpec ->
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
        easings = tweenSpecs.map { it.easing },
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
private fun AnimationSpecExample(
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

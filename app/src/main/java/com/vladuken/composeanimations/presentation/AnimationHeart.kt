package com.vladuken.composeanimations.presentation

import androidx.compose.animation.core.Animation
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.DecayAnimation
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


private val ANIMATION: Animation<Dp, AnimationVector1D> = TargetBasedAnimation(
    animationSpec = tween(1000),
    typeConverter = Dp.VectorConverter,
    initialValue = 0.dp,
    targetValue = 100.dp
)

//private val ANIMATION: Animation<Dp, AnimationVector1D> = DecayAnimation(
//    animationSpec = exponentialDecay(),
//    typeConverter = Dp.VectorConverter,
//    initialValue = 0.dp,
//    initialVelocityVector = AnimationVector1D(400f)
//)

@Composable
fun AnimationPresentationSample(
    modifier: Modifier = Modifier,
    animation: Animation<Dp, AnimationVector1D> = ANIMATION
) {
    var currentState by remember { mutableStateOf(0.dp) }
    var timeNanos by remember { mutableStateOf(0L) }
    var counter by remember { mutableStateOf(0) }

    LaunchedEffect(counter) {
        val startTime = withFrameNanos { it }
        do {
            val animationTime = withFrameNanos { it } - startTime
            timeNanos = animationTime
            currentState = animation.getValueFromNanos(animationTime)
        } while (!animation.isFinishedFromNanos(animationTime))
    }

    HeartWithSizeAndTime(
        modifier = modifier,
        currentState = currentState,
        timeMs = timeNanos / 1_000_000
    ) { counter++ }
}

@Composable
fun PresentationTimeWithHeart(
    modifier: Modifier = Modifier,
    timeMs: Long,
    animation: Animation<Dp, AnimationVector1D> = ANIMATION
) {
    val size = animation.getValueFromNanos(timeMs * 1_000_000)
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.width(70.dp),
            text = "$timeMs ms",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge
        )
        Icon(
            imageVector = Icons.Default.ArrowForward, contentDescription = null
        )
        Card {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Animation",
                style = MaterialTheme.typography.labelLarge
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForward, contentDescription = null
        )
        Card {
            HeartWithSizeAndTime(currentState = size, timeMs = null) {}
        }
    }
}

@Composable
private fun HeartWithSizeAndTime(
    modifier: Modifier = Modifier,
    maxSize: Dp = ANIMATION.targetValue,
    currentState: Dp,
    timeMs: Long?,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(maxSize),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(currentState)
                    .clickable { onClick() },
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red
            )
        }
        Text(
            text = "${currentState.value} dp",
            style = MaterialTheme.typography.labelLarge
        )
        timeMs?.let {
            Text(
                text = "$timeMs ms",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }

}

@Preview(
    showBackground = true,
    backgroundColor = 0x00_FF_FF_FF
)
@Composable
fun HeartAnimationPreview() {
    AnimationPresentationSample(
        modifier = Modifier.padding(32.dp)
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0x00_FF_FF_FF
)
@Composable
fun HeartExamplesAnimation() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PresentationTimeWithHeart(timeMs = 0)
        Spacer(modifier = Modifier.height(32.dp))
        PresentationTimeWithHeart(timeMs = 300)
        Spacer(modifier = Modifier.height(32.dp))
        PresentationTimeWithHeart(timeMs = 1000)
    }
}
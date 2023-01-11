package com.vladuken.composeanimations.animation

import androidx.annotation.FloatRange
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

/**
 * TargetBasedAnimation - имплементация Animation, которая позволяет нам явно контроллировать время выполнения анимации
 * - TargetBasedAnimation и параметры
 * - withFramesNano и suspend - почему и зачем
 * - getValueFromNanos
 * - isFinishedFromNanos
 */
@Composable
private fun AnimationSample(
    modifier: Modifier = Modifier,
    calculateNextFrameTimeNanos: suspend () -> Long
) {

    val animation = remember {
        TargetBasedAnimation(
            animationSpec = tween(1000),
            typeConverter = Float.VectorConverter,
            initialValue = 0f,
            targetValue = 1f
        )
    }

    var fraction by remember { mutableStateOf(0f) }
    var animationTime by remember { mutableStateOf(0L) }
    var animationFrame by remember { mutableStateOf(0) }

    LaunchedEffect(animation) {
        do {
            animationFrame++
            animationTime = calculateNextFrameTimeNanos()
            fraction = animation.getValueFromNanos(animationTime)
        } while (!animation.isFinishedFromNanos(animationTime))
    }

    AnimationDebugString(
        modifier = modifier,
        fraction = fraction,
        animationTime = animationTime,
        animationFrame = animationFrame,
        isAnimationFinished = animation.isFinishedFromNanos(animationTime)
    )
}

@Composable
private fun AnimationDebugString(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float,
    animationTime: Long,
    animationFrame: Int,
    isAnimationFinished: Boolean,
) {
    Column(modifier = modifier) {
        Text(
            text = "Time: %04d Frame: %03d IsFinished: %5b".format(
                animationTime / 1_000_000,
                animationFrame,
                isAnimationFinished
            ),
        )
        Spacer(
            modifier = Modifier
                .background(Color.Gray)
                .height(8.dp)
                .fillMaxWidth(fraction)
        )
    }

}

@Composable
fun FrameByFrameAnimation(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        var frameTimeMs by remember { mutableStateOf(0L) }
        var isPaused by remember { mutableStateOf(true) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                isPaused = false
                frameTimeMs += 100
            }) {
                Text(text = "Next Frame")
            }
            Button(onClick = {
                isPaused = false
                frameTimeMs = (frameTimeMs - 100).coerceAtLeast(0)
            }) {
                Text(text = "Prev Frame")
            }
        }
        AnimationSample {
            // TODO приостанавливать корутину до следующего клика
            withContext(Dispatchers.IO) {
                while (isPaused) yield()
                isPaused = true
            }
            withFrameNanos { frameTimeMs * 1_000_000 }
        }
    }
}


@Composable
fun AnimationSamplesScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        AnimationSample { withFrameNanos { it } }
        AnimationSample { withFrameNanos { it / 2 } }
        AnimationSample { withFrameNanos { it / 5 } }
        FrameByFrameAnimation()
    }
}


@Preview
@Composable
fun AnimationSamplePreview() {
    AnimationSamplesScreen()
}

//TODO Maybe move to withFrameMillis

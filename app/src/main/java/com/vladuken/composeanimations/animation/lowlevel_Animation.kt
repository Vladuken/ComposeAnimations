package com.vladuken.composeanimations.animation

import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animation
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.DecayAnimation
import androidx.compose.animation.core.FloatDecayAnimationSpec
import androidx.compose.animation.core.FloatExponentialDecaySpec
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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

val targetBasedAnimation = TargetBasedAnimation(
    animationSpec = tween(1000),
    typeConverter = Float.VectorConverter,
    initialValue = 0f,
    targetValue = 1f
)

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
    initialAnimation: Animation<Float, AnimationVector1D> = targetBasedAnimation,
    calculateNextFrameTimeNanos: suspend () -> Long
) {
    val animation = remember { initialAnimation }

    var fraction by remember { mutableStateOf(0f) }
    var animationTime by remember { mutableStateOf(0L) }
    var animationFrame by remember { mutableStateOf(0) }

    LaunchedEffect(animation) {
        val startTime = calculateNextFrameTimeNanos()

        do {
            animationFrame++
            animationTime = calculateNextFrameTimeNanos() - startTime
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
    Column(
        modifier = modifier
            .padding(vertical = 4.dp)
    ) {
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
    modifier: Modifier = Modifier,
    step: Long = 100,
    initialAnimation: Animation<Float, AnimationVector1D> = targetBasedAnimation,
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
                frameTimeMs += step
            }) {
                Text(text = "Next Frame")
            }
            Button(onClick = {
                isPaused = false
                frameTimeMs = (frameTimeMs - step).coerceAtLeast(0)
            }) {
                Text(text = "Prev Frame")
            }
        }
        AnimationSample(
            initialAnimation = initialAnimation
        ) {
            // TODO приостанавливать корутину до следующего клика
            withContext(Dispatchers.IO) {
                while (isPaused) yield()
                isPaused = true
            }
            withFrameNanos { frameTimeMs * 1_000_000 }
        }
    }
}

// То же самое что и TargetBasedAnimation , но вместо targetAnimation дает тебе initialVelocity
private fun provideDecayAnimation(
    animationSpec: FloatDecayAnimationSpec = FloatExponentialDecaySpec(),
    initialValue: Float = 0f,
    initialVelocity: Float = 1f
): DecayAnimation<Float, AnimationVector1D> {
    return DecayAnimation(
        animationSpec = animationSpec,
        initialValue = initialValue,
        initialVelocity = initialVelocity
    )
}

@Composable
private fun AnimationSamples(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        AnimationSample { withFrameNanos { it } }
        AnimationSample { withFrameNanos { it / 2 } }
        AnimationSample { withFrameNanos { it / 5 } }
        FrameByFrameAnimation()

        AnimationSample(
            initialAnimation = provideDecayAnimation(
                initialValue = 0f,
                initialVelocity = 1f
            )
        ) { withFrameNanos { it } }
        AnimationSample(
            initialAnimation = provideDecayAnimation(
                initialValue = 0f,
                initialVelocity = 2f
            )
        ) { withFrameNanos { it } }
        AnimationSample(
            initialAnimation = provideDecayAnimation(
                initialValue = 0f,
                initialVelocity = 3f
            )
        ) { withFrameNanos { it } }

        FrameByFrameAnimation(
            initialAnimation = provideDecayAnimation(
                initialValue = 0f,
                initialVelocity = 3f
            ),
            step = 25
        )
    }
}


@Composable
fun AnimationSamplesScreen(modifier: Modifier = Modifier) {
    var number by remember { mutableStateOf(0f) }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { number++ }) {
            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
        }
        key(number) {
            AnimationSamples()
        }
    }
}

@Preview
@Composable
fun AnimationSamplePreview() {
    AnimationSamplesScreen()
}

//TODO Maybe move to withFrameMillis
//TODO Подчистить код

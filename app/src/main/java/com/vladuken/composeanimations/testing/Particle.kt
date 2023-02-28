package com.vladuken.composeanimations.testing

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun Particle() {
    Box(
        modifier = Modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {

        val transitionState = remember {
            MutableTransitionState(0.1f).apply {
                targetState = 0f
            }
        }

        val transition = updateTransition(transitionState, label = "particle transition")

        val alpha by transition.animateFloat(
            transitionSpec = {
                keyframes {
                    durationMillis = PARTICLE_ANIMATION_DURATION
                    0f at 0
                    1f at (PARTICLE_ANIMATION_DURATION * 0.1f).toInt()
                    1f at (PARTICLE_ANIMATION_DURATION * 0.8f).toInt()
                    0f at PARTICLE_ANIMATION_DURATION
                }
            },
            label = "alpha animation of particle"
        ) { it }

        val scale by transition.animateFloat(
            transitionSpec = {
                keyframes {
                    durationMillis = PARTICLE_ANIMATION_DURATION
                    1f at 0
                    1f at (PARTICLE_ANIMATION_DURATION * 0.7f).toInt()
                    1.5f at PARTICLE_ANIMATION_DURATION
                }
            },
            label = "scale animation of particle"
        ) { it }

        Text(
            modifier = Modifier
                .wrapContentSize()
                .scale(scale)
                .alpha(alpha),
            text = "\uD83D\uDD25",
            fontSize = 50.sp
        )
    }
}

private const val PARTICLE_ANIMATION_DURATION = 2000


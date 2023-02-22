@file:OptIn(ExperimentalAnimationApi::class)

package com.vladuken.composeanimations.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
private fun HelperCodeSamples() {
    var stringContent by remember {
        mutableStateOf("String Content")
    }
    val transition = updateTransition(
        targetState = stringContent
    )

    transition.Crossfade { content ->
        Text(text = content)
    }

    transition.AnimatedContent(
        transitionSpec = {
            fadeIn(tween(300)) with
                    fadeOut(tween(300))
        },
    ) { content ->
        Text(text = content)
    }
}

@Composable
private fun HelperVisibilitySample() {
    var visible by remember { mutableStateOf(true) }
    val transition = updateTransition(targetState = visible)

    transition.AnimatedVisibility(
        visible = { isVisible -> isVisible }
    ) {
        Text(text = "Hello")
    }
}
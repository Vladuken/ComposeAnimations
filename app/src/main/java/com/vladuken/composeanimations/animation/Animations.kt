package com.vladuken.composeanimations.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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


@Composable
fun AnimatedVisibilityExample(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    text: String = "Hello Android",
    enter: EnterTransition,
    exit: ExitTransition,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = enter,
        exit = exit
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun AnimatedVisibilityWithTransitionStateExample(
    modifier: Modifier = Modifier,
    isVisible: MutableTransitionState<Boolean>,
    text: String = "Hello Android",
    enter: EnterTransition,
    exit: ExitTransition,
) {
    AnimatedVisibility(
        visibleState = isVisible,
        modifier = modifier,
        enter = enter,
        exit = exit
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedVisibilityExamplePreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var visible by remember { mutableStateOf(true) }
        Button(onClick = { visible = !visible }) {
            Text(text = if (visible) "Hide" else "Show")
        }
        // Simple FadeIn/Out
        FadeSimple(visible)
        // FadeIn/Out with SlideIn/Out
        FadeWithSlide(visible)
        // Custom Offset SlideIn/Out
        FadeWithSliceCustomOffset(visible)
        // MutableTransitionState
        AnimationWithMutableTransitionState()
    }
}

@Composable
private fun FadeSimple(visible: Boolean) {
    AnimatedVisibilityExample(
        isVisible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    )
}


@Composable
private fun FadeWithSlide(visible: Boolean) {
    AnimatedVisibilityExample(
        isVisible = visible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally(),
    )
}

@Composable
private fun FadeWithSliceCustomOffset(visible: Boolean) {
    AnimatedVisibilityExample(
        isVisible = visible,
        enter = fadeIn() + slideInHorizontally {
            -it * 2
        },
        exit = fadeOut() + slideOutHorizontally {
            it * 2
        },
    )
}

@Composable
private fun AnimationWithMutableTransitionState() {
    val state = remember { MutableTransitionState(true) }
    val color = when {
        state.isIdle && state.currentState -> Color.Green
        !state.isIdle && state.currentState -> Color.Yellow
        state.isIdle && !state.currentState -> Color.Red
        !state.isIdle && !state.currentState -> Color.Blue
        else -> error("Illegal State $state")
    }
    Button(
        onClick = { state.targetState = !state.targetState },
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.Black
        )
    ) {
        Text(text = if (state.currentState) "Hide" else "Show")
    }
    AnimatedVisibilityWithTransitionStateExample(
        isVisible = state,
        enter = fadeIn() + slideInHorizontally { -it * 2 },
        exit = fadeOut() + slideOutHorizontally { it * 2 },
    )
}

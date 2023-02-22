@file:OptIn(ExperimentalAnimationApi::class)

package com.vladuken.composeanimations.api.samples

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vladuken.composeanimations.api.core.ShowHideButton

const val HELLO_ANDROID = "Hello Android"

@Composable
fun AnimatedVisibilityScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // AnimatedVisibility : Basics
        AnimationBasics()
        // AnimatedVisibility + MutableTransitionState
        AnimationWithMutableTransitionState()
        // AnimatedVisibility + Modifier for children
        AnimatedVisibilityWithChildren()
        // AnimatedVisibility + AnimationSpec + plus()
        AnimatedVisibilityPlusExample()
    }
}

@Composable
private fun AnimationBasics() {
    var visible by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowHideButton(
            visible = visible,
            onClick = { visible = !visible }
        )
        // Simple FadeIn/Out
        FadeSimple(visible)
        // FadeIn/Out with SlideIn/Out
        FadeWithSlide(visible)
        // Custom Offset SlideIn/Out
        FadeWithSliceCustomOffset(visible)
    }
}


/**
 * Animations
 */

@Composable
private fun FadeSimple(visible: Boolean) {
    AnimatedVisibilitySample(
        isVisible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    )
}

@Composable
private fun FadeWithSlide(visible: Boolean) {
    AnimatedVisibilitySample(
        isVisible = visible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally(),
    )
}

@Composable
private fun FadeWithSliceCustomOffset(visible: Boolean) {
    AnimatedVisibilitySample(
        isVisible = visible,
        enter = fadeIn() + slideInHorizontally { -it * 2 },
        exit = fadeOut() + slideOutHorizontally { it * 2 },
    )
}

/**
 * Composable helper function - AnimatedVisibility wrapper
 */
@Composable
private fun AnimatedVisibilitySample(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    text: String = HELLO_ANDROID,
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
private fun AnimationWithMutableTransitionState() {
    val state = remember { MutableTransitionState(true) }
    val color = when {
        // Appearing
        !state.isIdle && !state.currentState -> Color.Blue
        // Appeared
        state.isIdle && state.currentState -> Color.Green
        // Hiding
        !state.isIdle && state.currentState -> Color.Yellow
        // Hidden
        state.isIdle && !state.currentState -> Color.Red
        else -> error("Illegal State $state")
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowHideButton(
            visible = state.currentState,
            onClick = { state.targetState = !state.targetState },
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = Color.Black
            )
        )
        AnimatedVisibilityTransitionState(
            isVisible = state,
            enter = fadeIn() + slideInHorizontally { -it * 2 },
            exit = fadeOut() + slideOutHorizontally { it * 2 },
        )
    }
}

@Composable
private fun AnimatedVisibilityTransitionState(
    modifier: Modifier = Modifier,
    isVisible: MutableTransitionState<Boolean>,
    text: String = HELLO_ANDROID,
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedVisibilityWithChildren(
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(true) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowHideButton(
            visible = visible,
            onClick = { visible = !visible }
        )
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            Box {
                Column {
                    Text(
                        modifier = Modifier
                            .animateEnterExit(
                                enter = slideInHorizontally(),
                                exit = slideOutHorizontally()
                            ),
                        text = "$HELLO_ANDROID : 1",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(
                        modifier = Modifier
                            .animateEnterExit(
                                enter = fadeIn(),
                                exit = fadeOut()
                            ),
                        text = "$HELLO_ANDROID : 2",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(
                        modifier = Modifier
                            .animateEnterExit(
                                enter = scaleIn(),
                                exit = scaleOut()
                            ),
                        text = "$HELLO_ANDROID : 3",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedVisibilityPlusExample() {
    var visible by remember { mutableStateOf(false) }
    val duration = 1500
    Column {
        ShowHideButton(
            visible = visible,
            onClick = { visible = !visible }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedBox(
                isVisible = visible,
                enter = fadeIn(tween(duration)),
                exit = fadeOut(tween(duration))
            )
            Text(
                text = "+",
                style = MaterialTheme.typography.headlineLarge
            )

            AnimatedBox(
                isVisible = visible,
                enter = scaleIn(tween(duration)),
                exit = scaleOut(tween(duration))
            )
            Text(
                text = "=",
                style = MaterialTheme.typography.headlineLarge
            )
            AnimatedBox(
                isVisible = visible,
                enter = fadeIn(tween(duration)) + scaleIn(tween(duration)),
                exit = fadeOut(tween(duration)) + scaleOut(tween(duration))
            )
        }
    }
}

@Composable
private fun AnimatedBox(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    enter: EnterTransition,
    exit: ExitTransition,
) {

    Card(
        modifier = modifier.size(60.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isVisible,
                enter = enter,
                exit = exit
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Blue)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0x00_FF_FF_FF
)
@Composable
fun AnimatedVisibilityScreenPreview() {
    AnimatedVisibilityScreen()
}

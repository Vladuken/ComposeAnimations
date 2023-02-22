@file:OptIn(ExperimentalAnimationApi::class)

package com.vladuken.composeanimations.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

const val HELLO_ANDROID = "Hello Android"

@Composable
fun AnimatedVisibilitySample(
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
fun AnimatedVisibilityTransitionState(
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


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityWithChildren(
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
fun AnimatedVisibilityScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var visible by remember { mutableStateOf(true) }
        ShowHideButton(
            visible = visible,
            onClick = { visible = !visible }
        )
        // Simple FadeIn/Out
        /**
         * Здесь можно начать с рассказа о том, как сделать самую простую анимацию с AnimatedVisibility
         */
        FadeSimple(visible)
        // FadeIn/Out with SlideIn/Out
        /**
         * Далее указать, что анимации можно конкатенировать, и таким образом делать свою кастомную анимацию
         */
        FadeWithSlide(visible)
        // Custom Offset SlideIn/Out
        /**
         * Слудующий шаг - указать, что для анимаций можно сделать кастомный offset
         * - В этот же момент можно показать, что анимации в Compose прерываемы:
         * если их остановить во время выполнения, то они плавно вернутся в исходное положение
         */
        FadeWithSliceCustomOffset(visible)

        // MutableTransitionState
        /**
         * MutableTransitionState - рассказать про этот стейтхолдер,
         * который позволяет отслеживать текущее состояние анимации
         */
        AnimationWithMutableTransitionState()
        // AnimatedVisibilityWithChildren
        /**
         * Дополнительная возможность AnimatedVisibility - предоставлять Modifier.animateEnterExit,
         * позволяющий указывать кастомные анимации для children
         * Собственно, тут можно сказать, что те три анимации, которые мы сделали выше - можно сделать проще
         */
        AnimatedVisibilityWithChildren()
        /**
         * Далее можно упомянуть что сейчас, когда элемент становится невидимым, он также не занимает место
         * И чтобы сохранить место - нужно использовать другой способ анимации - плавный переход к animate*AsState
         */
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibilityPlusExample()
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
fun AnimatedBox(
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

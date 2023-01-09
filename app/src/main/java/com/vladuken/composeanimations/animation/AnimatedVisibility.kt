package com.vladuken.composeanimations.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedVisibilityScreenPreview() {
    AnimatedVisibilityScreen()
}

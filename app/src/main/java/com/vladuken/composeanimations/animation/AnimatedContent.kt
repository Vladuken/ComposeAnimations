package com.vladuken.composeanimations.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedContentSample(
    modifier: Modifier = Modifier,
    initialValue: Int = 0,
    transitionSpec: AnimatedContentScope<Int>.() -> ContentTransform
) {
    var count by remember { mutableStateOf(initialValue) }
    Button(
        modifier = modifier.fillMaxWidth(0.5f),
        onClick = { count++ },
    ) {
        AnimatedContent(
            targetState = count,
            transitionSpec = transitionSpec
        ) { targetCount ->
            Text(text = "$targetCount")
        }
    }
}


@Composable
@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /**
         * 1 Простые примеры анимаций
         */
        AnimatedContentSample { fadeIn() with fadeOut() }
        /**
         * 2 Простой пример анимации слева-направо без SizeTransform
         * Анимация словно обрезается и располагается в маленьком прямоугольнике
         */
        AnimatedContentSample {
            slideInHorizontally { -it } + fadeIn() with
                slideOutHorizontally { it } + fadeOut()
        }
        /**
         * 3 Пример с Size Transform
         * Решает проблему из прошлого примера, добавляя SizeTransform вместе с clip = false
         * Рассказать что clip = true по дефолту, чтобы [WIP]
         */
        AnimatedContentSample {
            slideInHorizontally { -it } + fadeIn() with
                slideOutHorizontally { it } + fadeOut() using
                SizeTransform(clip = false)
        }
        /**
         *  TODO Придумать кейс с SizeTransform + keyframes{}
         *  4 Возможно получится починить баг с врертикальным расширением при переходе 9->10
         */
//        AnimatedContentSample {
//            slideInHorizontally { -it } + fadeIn() with
//                slideOutHorizontally { it } + fadeOut() using
//                SizeTransform(clip = false) { initialSize, targetSize ->
//                    keyframes {
//                        IntSize(targetSize.width, initialSize.height) at 300
//                        durationMillis = 300
//                    }
//                }
//        }
    }
}


@Preview(showBackground = true)
@Composable
fun AnimatedContentPreview() {
    AnimatedContentScreen()
}

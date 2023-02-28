package com.vladuken.composeanimations.api.samples

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Animated Content : Basics
        AnimatedContentSample { fadeIn() with fadeOut() }

        // Animated Content + Custom Enter/ExitTransitions
        AnimatedContentSample {
            slideInHorizontally { -it } + fadeIn() with
                    slideOutHorizontally { it } + fadeOut()
        }
        // Animated Content + Custom Enter/ExitTransitions + SizeTransform
        AnimatedContentSample {
            slideInHorizontally { -it } + fadeIn() with
                    slideOutHorizontally { it } + fadeOut() using
                    SizeTransform(clip = false)
        }
        /**
         *  TODO WIP
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedContentSample(
    modifier: Modifier = Modifier,
    initialValue: Int = 0,
    transitionSpec: AnimatedContentScope<Int>.() -> ContentTransform
) {
    var count by remember { mutableStateOf(initialValue) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = modifier.fillMaxWidth(0.5f),
            onClick = { count++ },
        ) {
            Text(text = "+")
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedContent(
            targetState = count,
            transitionSpec = transitionSpec
        ) { targetCount ->
            Text(
                text = "$HELLO_ANDROID : $targetCount",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0x00_FF_FF_FF
)
@Composable
fun AnimatedContentPreview() {
    AnimatedContentScreen()
}

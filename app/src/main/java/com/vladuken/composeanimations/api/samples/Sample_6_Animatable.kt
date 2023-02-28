package com.vladuken.composeanimations.api.samples

import androidx.compose.animation.Animatable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AnimatableSamplesScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatableSampleToggleTwoColors(
            text = "Toggle Animatable 2 Colors",
            firstColor = Color.Blue,
            secondColor = Color.Green
        )
        AnimatableSampleWithList(
            text = "Toggle Animatable Color List",
            colors = listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue, Color.Magenta)
        )
    }
}


/**
 * Toggle between [firstColor] and [secondColor]
 * Initially colored into [initialColor]
 */
@Composable
private fun AnimatableSampleToggleTwoColors(
    text: String,
    firstColor: Color,
    secondColor: Color,
    initialColor: Color = Color.Gray,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    var isVisible by remember { mutableStateOf(true) }
    val color = remember { Animatable(initialColor) }

    Column {
        Button(
            onClick = {
                isVisible = !isVisible
                scope.launch {
                    color.animateTo(if (isVisible) firstColor else secondColor)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = color.value
            )
        ) {
            Text(text = text)
        }
    }
}

/**
 * Same as [AnimatableSampleToggleTwoColors] but with list of colors and initial color.
 */
@Composable
private fun AnimatableSampleWithList(
    text: String,
    colors: List<Color>,
    initialColor: Color = Color.Gray,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    var count by remember { mutableStateOf(0) }
    val color = remember { Animatable(initialColor) }

    Column {
        Button(
            onClick = {
                count++
                scope.launch {
                    color.animateTo(colors[count % colors.size])
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = color.value
            )
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun AnimatableSamplePreview() {
    AnimatableSamplesScreen()
}

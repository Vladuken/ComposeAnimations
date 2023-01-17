package com.vladuken.composeanimations.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateRect
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class BoxState {
    Collapsed,
    Half,
    Expanded
}

@Composable
fun TransitionElement() {
    var state: BoxState by remember { mutableStateOf(BoxState.Collapsed) }

    Column {
        ShowHideButton(
            visible = state == BoxState.Expanded,
            onClick = {
                state = when (state) {
                    BoxState.Collapsed -> BoxState.Half
                    BoxState.Expanded -> BoxState.Collapsed
                    BoxState.Half -> BoxState.Expanded
                }
            }
        )
        TransitionSample(state = state)
    }
}

@Composable
fun TransitionSample(
    modifier: Modifier = Modifier,
    state: BoxState
) {

    val transition = updateTransition(
        label = "Parent Animation",
        targetState = state
    )
    val rect by transition.animateRect(
        label = "Animate Rect",
        transitionSpec = {
            when {
                BoxState.Collapsed isTransitioningTo BoxState.Expanded -> spring()
                else -> tween(300)
            }
        }
    ) {
        when (it) {
            BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
            BoxState.Half -> Rect(0f, 0f, 300f, 100f)
            BoxState.Expanded -> Rect(0f, 0f, 300f, 300f)
        }
    }
    val borderWidth by transition.animateDp(
        label = "Animate Dp",
    ) {
        when (it) {
            BoxState.Collapsed -> 2.dp
            BoxState.Half -> 4.dp
            BoxState.Expanded -> 8.dp
        }
    }

    val color by transition.animateColor(
        label = "Animate Color",
    ) {
        when (it) {
            BoxState.Collapsed -> Color.Green
            BoxState.Expanded -> Color.Black
            BoxState.Half -> Color.Gray
        }
    }


    Box(
        modifier = modifier
            .size(rect.width.dp, rect.height.dp)
            .border(borderWidth, color)
    ) {
        Text(
            modifier = Modifier.padding(borderWidth),
            text = "Sample Text"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransitionElementPreview() {
    TransitionElement()
}

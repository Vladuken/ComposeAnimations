package com.vladuken.composeanimations.presentation

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vladuken.composeanimations.animation.ShowHideButton

@Composable
fun AnimateMultipleStates(
    animationDuration: Int = 500
) {
    var active by remember { mutableStateOf(true) }

    val rotation: Rotation by animateRotationAsState(
        if (active) Rotation(0f) else Rotation(180f), animationSpec = tween(animationDuration)
    )
    val color: Color by animateColorAsState(
        if (active) Color.Red else Color.Blue, animationSpec = tween(animationDuration)
    )

    val size: Dp by animateDpAsState(
        if (active) 60.dp else 150.dp, animationSpec = tween(animationDuration)
    )

    ComponentWithButtonAndCube(active, color, size, rotation)
}


@Composable
fun AnimateMultipleTransitionStates() {
    var active by remember { mutableStateOf(true) }
    val transition = updateTransition(
        targetState = active, label = "Parent"
    )

    val rotation by transition.animateValue(
        typeConverter = RotationConverter,
        transitionSpec = { tween(500) },
        label = "Animate Rotation"
    ) {
        when (it) {
            true -> Rotation(0f)
            false -> Rotation(180f)
        }
    }
    val color: Color by transition.animateColor(
        transitionSpec = { tween(500) }, label = "Animate Color"
    ) {
        if (it) Color.Red else Color.Blue
    }

    val size: Dp by transition.animateDp(
        transitionSpec = { tween(500) }, label = "Animate Dp"
    ) {
        if (it) 60.dp else 150.dp
    }

    ComponentWithButtonAndCube(active, color, size, rotation)
}

/**
 * Helper composable
 */
@Composable
private fun ComponentWithButtonAndCube(
    active: Boolean, color: Color, size: Dp, rotation: Rotation
) {
    var active1 = active
    Column(
        modifier = Modifier.size(400.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowHideButton(visible = active1, onClick = { active1 = !active1 })
        AnimatedSquare(
            color = color,
            size = size,
            rotation = rotation,
        )
    }
}

@Composable
private fun AnimatedSquare(
    color: Color = Color.Red, rotation: Rotation = Rotation(0f), size: Dp = 60.dp
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .rotate(rotation.value)
                .background(color)
        )
    }
}

@Preview(
    showBackground = true, backgroundColor = 0x00_FF_FF_FF
)
@Composable
fun AnimateMultipleStatesPreview() {
    Column {
        AnimateMultipleStates()
        AnimateMultipleTransitionStates()
    }
}

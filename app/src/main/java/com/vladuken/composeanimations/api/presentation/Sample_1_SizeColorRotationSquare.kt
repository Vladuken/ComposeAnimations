package com.vladuken.composeanimations.api.presentation

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import com.vladuken.composeanimations.api.core.ShowHideButton


@Composable
fun SizeColorRotationSquareScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AnimateMultipleStates()
        AnimateMultipleTransitionStates()
        EncapsulateTransitionStates()
    }
}

/**
 * Animate With animateAsState()
 */
@Composable
private fun AnimateMultipleStates(
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

    ComponentWithButtonAndCube(active, color, size, rotation) {
        active = !active
    }
}


@Composable
private fun AnimateMultipleTransitionStates() {
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

    ComponentWithButtonAndCube(active, color, size, rotation) {
        active = !active
    }
}

/**
 * Encapsulate Transition
 * TODO Improve For Video
 */
@Composable
private fun EncapsulateTransitionStates() {
    var active by remember { mutableStateOf(true) }
    val transition = updateTransitionData(active)
    Column(
        modifier = Modifier.size(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowHideButton(
            visible = active,
            onClick = { active = !active }
        )
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            AnimatedSquare(
                modifier = Modifier.weight(1f),
                color = transition.color.value,
                size = transition.size.value,
                rotation = transition.rotation.value,
            )
            AnimatedIcon(
                modifier = Modifier.weight(1f),
                color = transition.color.value,
                size = transition.size.value,
                rotation = transition.rotation.value,
            )
        }

    }
}

private data class AnimState(
    val rotation: State<Rotation>,
    val size: State<Dp>,
    val color: State<Color>
)

@Composable
private fun updateTransitionData(isActive: Boolean): AnimState {
    val transition = updateTransition(
        label = "Animate Size",
        targetState = isActive
    )

    val color = transition.animateColor(
        label = "Animate Color",
        transitionSpec = { tween(500) }
    ) { state ->
        if (state) Color.Red else Color.Blue
    }
    val size = transition.animateDp(
        label = "Animate Size",
        transitionSpec = { tween(500) }
    ) { state ->
        if (state) 60.dp else 150.dp
    }
    val rotation = transition.animateValue(
        label = "Animate Rotation",
        transitionSpec = { tween(500) },
        typeConverter = RotationConverter
    ) { state ->
        when (state) {
            true -> Rotation(0f)
            false -> Rotation(180f)
        }
    }

    return remember(transition) { AnimState(rotation, size, color) }
}

/**
 * Example Helpers
 */
@Composable
private fun ComponentWithButtonAndCube(
    active: Boolean,
    color: Color,
    size: Dp,
    rotation: Rotation,
    toggle: () -> Unit = {}
) {
    Column(
        modifier = Modifier.size(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowHideButton(visible = active, onClick = toggle)
        AnimatedSquare(
            color = color,
            size = size,
            rotation = rotation,
        )
    }
}

@Composable
private fun AnimatedSquare(
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
    rotation: Rotation = Rotation(0f),
    size: Dp = 60.dp
) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .rotate(rotation.value)
                .background(color)
        )
    }
}

@Composable
private fun AnimatedIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
    rotation: Rotation = Rotation(0f),
    size: Dp = 60.dp
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(size)
                .rotate(rotation.value),
            imageVector = Icons.Default.Settings,
            contentDescription = null,
            tint = color
        )
    }
}

/**
 * Previews
 */
@Preview(
    showBackground = true, backgroundColor = 0x00_FF_FF_FF
)
@Composable
fun AnimateMultipleStatesPreview() {
    Column {
        AnimateMultipleStates()
        AnimateMultipleTransitionStates()
        EncapsulateTransitionStates()
    }
}
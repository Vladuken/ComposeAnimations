package com.vladuken.composeanimations.presentation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
fun animateRotationAsState(
    targetValue: Rotation,
    animationSpec: AnimationSpec<Rotation> = spring(),
): State<Rotation> {
    return animateValueAsState(
        targetValue = targetValue,
        typeConverter = RotationConverter,
        animationSpec = animationSpec
    )
}

val RotationConverter = TwoWayConverter<Rotation, AnimationVector1D>(
    convertToVector = { AnimationVector1D(it.value) },
    convertFromVector = { Rotation(it.value) }
)

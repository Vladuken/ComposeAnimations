package com.vladuken.composeanimations.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vladuken.composeanimations.animation.AnimationDebugString

@Composable
private fun InfiniteLineAnimation(
    modifier: Modifier = Modifier
) {
    val animatable = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = tween(1000)
            )
            animatable.snapTo(0f)
        }
    }
    AnimationDebugString(
        modifier = modifier,
        fraction = animatable.value,
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0x00_FF_FF_FF
)
@Composable
fun PreviewSnapTo() {
    InfiniteLineAnimation()
}
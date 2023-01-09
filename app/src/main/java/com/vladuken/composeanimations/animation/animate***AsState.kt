package com.vladuken.composeanimations.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimateFloatAsState() {
    var visible by remember { mutableStateOf(true) }
    val alpha: Float by animateFloatAsState(if (visible) 1f else 0.0f)
    val color: Color by animateColorAsState(if (visible) Color.Green else Color.Blue)
    val dp: Dp by animateDpAsState(if (visible) 0.dp else 32.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowHideButton(
            visible = visible,
            onClick = { visible = !visible }
        )
        Text(
            modifier = Modifier.alpha(alpha),
            text = HELLO_ANDROID,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = HELLO_ANDROID,
            color = color,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            modifier = Modifier
                .background(Color.Gray)
                .padding(horizontal = dp),
            text = HELLO_ANDROID,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimateFloatAsStatePreview() {
    AnimateFloatAsState()
}

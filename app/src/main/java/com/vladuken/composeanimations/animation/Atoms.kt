package com.vladuken.composeanimations.animation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShowHideButton(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onClick: () -> Unit,
    showText: String = "Show",
    hideText: String = "Hide",
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = colors
    ) {
        Text(text = if (visible) showText else hideText)
    }
}


@Composable
fun AnimationSpot(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
        Surface(
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 8.dp
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun AnimationSpotPreview() {
    AnimationSpot(title = "Test Title") {
        Text(text = "Content")
    }
}

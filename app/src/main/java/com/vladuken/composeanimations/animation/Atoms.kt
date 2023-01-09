package com.vladuken.composeanimations.animation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShowHideButton(
    visible: Boolean,
    onClick: () -> Unit,
    showText: String = "Show",
    hideText: String = "Hide",
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {
    Button(
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
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
        content()
    }
}

@Preview
@Composable
fun AnimationSpotPreview() {
    AnimationSpot(title = "Test Title") {
        Text(text = "Content")
    }
}

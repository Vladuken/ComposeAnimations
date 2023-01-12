package com.vladuken.composeanimations.animation

import androidx.annotation.FloatRange
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    Surface(
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var isContentVisible by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isContentVisible = !isContentVisible }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f),
                    text = title,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(32.dp),
                    imageVector = if (isContentVisible) {
                        Icons.Filled.KeyboardArrowUp
                    } else {
                        Icons.Filled.KeyboardArrowDown
                    },
                    contentDescription = null
                )
            }
            AnimatedVisibility(visible = isContentVisible) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
fun AnimationDebugString(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float,
    text: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = text)
        Spacer(
            modifier = Modifier
                .background(Color.Gray)
                .height(8.dp)
                .fillMaxWidth(fraction)
        )
    }
}

@Preview
@Composable
fun AnimationDebugStringPreview() {
    AnimationDebugString(
        text = "Some Temple Text",
        fraction = 0.5f
    )
}

@Preview
@Composable
fun AnimationSpotPreview() {
    AnimationSpot(title = "Test Title") {
        Text(text = "Content")
    }
}

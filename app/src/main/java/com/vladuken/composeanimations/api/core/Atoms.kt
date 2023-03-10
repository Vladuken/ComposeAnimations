package com.vladuken.composeanimations.api.core

import androidx.annotation.FloatRange
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
fun AnimationSlot(
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
fun AnimationLineString(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float,
    text: String? = null,
    baserFraction: Float = 0.8f,
    pinColor: Color = Color.DarkGray
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        if (text != null) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            val startOffset = maxWidth * (1f - baserFraction)
            Spacer(
                modifier = Modifier
                    .padding(horizontal = startOffset)
                    .fillMaxWidth()
                    .height(4.dp)
                    .alpha(0.2f)
                    .background(Color.Gray)
            )
            val pinSize = 16.dp
            Card(
                modifier = Modifier
                    .size(pinSize)
                    .offset(x = startOffset + (maxWidth - startOffset * 2) * fraction - pinSize / 2),
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = pinColor
                )
            ) {}
        }
    }
}

@Composable
fun GroupHeader(heading: String) {
    Text(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        text = heading,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun AnimationLineStringPreview() {
    AnimationLineString(
        text = "Some Temple Text",
        fraction = 0.5f
    )
}

@Preview
@Composable
fun AnimationSlotPreview() {
    AnimationSlot(title = "Test Title") {
        Text(text = "Content")
    }
}

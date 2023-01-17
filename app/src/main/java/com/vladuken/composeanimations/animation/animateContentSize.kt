package com.vladuken.composeanimations.animation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun AnimatedContentSizeSample(
    modifier: Modifier = Modifier,
    initialValue: Int = 1,
) {
    var count by remember { mutableStateOf(initialValue) }
    Column(
        modifier = modifier,
    ) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { count++ }) {
                Text(text = "Add")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { count = 1 }) {
                Text(text = "Clear")
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        /**
         * Пример без .animateContentSize()
         */
        Row(
            modifier = Modifier.background(Color.Gray)
        ) {
            repeat(count) { Text(text = "***") }
        }
        Spacer(modifier = Modifier.height(8.dp))
        /**
         * Пример с .animateContentSize()
         */
        Row(
            modifier = Modifier
                .background(Color.Gray)
                .animateContentSize(),
        ) {
            repeat(count) { Text(text = "***") }
        }
    }

}


@Composable
fun AnimatedContentSizeScreen(modifier: Modifier = Modifier) {
    AnimatedContentSizeSample(modifier.fillMaxWidth())
}


@Preview(showBackground = true)
@Composable
fun AnimatedContentSizePreview() {
    AnimatedContentSizeSample()
}

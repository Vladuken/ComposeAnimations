package com.vladuken.composeanimations.core

import androidx.compose.animation.core.Easing
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun EasingGraph(
    modifier: Modifier = Modifier,
    currentFraction: Float,
    easings: List<Easing>,
    colors: List<Color>,
    xAxisLabel: String = "x",
    yAxisLabel: String = "t"
) {
    val path by remember { mutableStateOf(Path()) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = yAxisLabel,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            XYGraph(
                path = path,
                easings = easings,
                currentFraction = currentFraction,
                colors = colors
            )
            Text(
                text = xAxisLabel,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

}

@Composable
private fun XYGraph(
    modifier: Modifier = Modifier,
    path: Path,
    easings: List<Easing>,
    currentFraction: Float,
    colors: List<Color>
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f)
    ) {
        // Draw Axis
        path.reset()
        path.lineTo(0f, size.height)
        path.lineTo(size.width, size.height)
        drawPath(
            path = path,
            color = Color.LightGray,
            style = Stroke(width = 3f),
        )

        // Draw function
        easings.forEachIndexed { index, easing ->
            path.reset()
            path.moveTo(0f, size.height)
            generateSequence(0f) { it + 0.01f }
                .takeWhile { it <= currentFraction }
                .forEach {
                    path.lineTo(
                        size.width * easing.transform(it),
                        size.height - size.height * it
                    )
                }

            drawPath(
                path = path,
                color = colors[index],
                style = Stroke(width = 4f),
            )
        }
    }
}
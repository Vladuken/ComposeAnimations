package com.vladuken.composeanimations.canvasanim

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.roundToInt

@Preview
@Composable
fun BouncyRopes() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {


        val offsetDot1 = remember { mutableStateOf(Offset(200f, 300f)) }
        val offsetDot2 = remember { mutableStateOf(Offset(400f, 300f)) }
        val offsetDot3 = remember { mutableStateOf(Offset(600f, 300f)) }
        val offsetDot4 = remember { mutableStateOf(Offset(800f, 300f)) }
        val dotSize = 16.dp
        val pxValuePx = with(LocalDensity.current) { 16.dp.toPx() }

        Box {
            Dot(
                size = dotSize,
                offset = offsetDot1,
            )

            Dot(
                size = dotSize,
                offset = offsetDot2,
            )

            Dot(
                size = dotSize,
                offset = offsetDot3,
            )

            Dot(
                size = dotSize,
                offset = offsetDot4,
            )
        }

        Line(
            dotSizePx = pxValuePx,
            offsetDotA = offsetDot1,
            offsetDotB = offsetDot2,
        )

        Line(
            dotSizePx = pxValuePx,
            offsetDotA = offsetDot2,
            offsetDotB = offsetDot3,
        )

        Line(
            dotSizePx = pxValuePx,
            offsetDotA = offsetDot3,
            offsetDotB = offsetDot4,
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun Line(
    dotSizePx: Float,
    offsetDotA: MutableState<Offset>,
    offsetDotB: MutableState<Offset>,
) {
    fun MutableState<Offset>.getDotCenterOffset() = Offset(
        x = value.x + dotSizePx / 2,
        y = value.y + dotSizePx / 2,
    )

    val path by remember { mutableStateOf(Path()) }
    val horizontalDelta by remember { derivedStateOf { abs(offsetDotA.value.x - offsetDotB.value.x) / 2 } }

    val midAnimatedPointOffset = animateOffsetAsState(
        targetValue = Offset(
            x = minOf(offsetDotA.value.x, offsetDotB.value.x) + horizontalDelta + dotSizePx / 2,
            y = maxOf(offsetDotA.value.y, offsetDotB.value.y) + horizontalDelta + dotSizePx / 2,
        ),
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = 105f),
    )
//
//    val midAnimatedPointOffset = mutableStateOf(
//        Offset(
//            x = minOf(offsetDotA.value.x, offsetDotB.value.x) + horizontalDelta + dotSizePx / 2,
//            y = maxOf(offsetDotA.value.y, offsetDotB.value.y) + horizontalDelta + dotSizePx / 2,
//        )
//    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        path.reset()
        path.moveTo(x = offsetDotA.getDotCenterOffset().x, y = offsetDotA.getDotCenterOffset().y)
        path.quadraticBezierTo(
            x1 = midAnimatedPointOffset.value.x,
            y1 = midAnimatedPointOffset.value.y,
            x2 = offsetDotB.getDotCenterOffset().x,
            y2 = offsetDotB.getDotCenterOffset().y,
        )
        drawPath(
            path = path,
            color = Color.Black,
            style = Stroke(width = 5f),
        )

        drawLine(
            Color.Red,
            offsetDotA.getDotCenterOffset(),
            midAnimatedPointOffset.value,
        )

        drawLine(
            Color.Red,
            midAnimatedPointOffset.value,
            offsetDotB.getDotCenterOffset(),
        )

        drawCircle(
            color = Color.Red,
            radius = 10f,
            center = Offset(midAnimatedPointOffset.value.x, midAnimatedPointOffset.value.y)
        )
    }
}


@Composable
fun Dot(size: Dp, offset: MutableState<Offset>) {
    Box(
        modifier = Modifier
            .offset { IntOffset(offset.value.x.roundToInt(), offset.value.y.roundToInt()) }
            .size(size)
            .background(Color.Black, shape = CircleShape)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offset.value += dragAmount
                }
            }
    )
}

@Preview
@Composable
fun DotPreview() {
    val offsetDot1 = remember { mutableStateOf(Offset(200f, 300f)) }
    Box(modifier = Modifier.fillMaxSize()) {
        Dot(size = 16.dp, offset = offsetDot1)
    }
}
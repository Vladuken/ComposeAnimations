package com.vladuken.composeanimations.presentation

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp


private enum class Location {
    HOME,
    WORK,
    CINEMA,
}

fun IntOffset.toPersonOffset(
    smallSize: Int,
    personSize: Int
): IntOffset {
    return copy(
        x = x + (smallSize - personSize) / 2,
        y = y + (smallSize - personSize) / 2
    )
}


@Composable
fun HomeWorkCinemaWithView() {
    var currentState by remember { mutableStateOf(Location.HOME) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(onClick = { currentState = Location.HOME }) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
            }
            IconButton(onClick = { currentState = Location.WORK }) {
                Icon(imageVector = Icons.Default.Build, contentDescription = null)
            }
            IconButton(onClick = { currentState = Location.CINEMA }) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
            }
        }

        val transition = updateTransition(
            targetState = currentState
        )
        CanvasAnimations(
            transition = transition
        )
    }
}

@Composable
private fun CanvasAnimations(
    canvasSize: Int = 300,
    smallSize: Int = 70,
    personSize: Int = 30,
    transition: Transition<Location>,
) {
    val workOffset: IntOffset = IntOffset(
        x = 0,
        y = 0
    )
    val homeOffset: IntOffset = IntOffset(
        x = (canvasSize - smallSize) / 2,
        y = canvasSize - smallSize
    )
    val cinemaOffset: IntOffset = IntOffset(
        x = canvasSize - smallSize,
        y = 0
    )

    val personOffset by transition.animateIntOffset(
        label = "Position Animation",
        transitionSpec = {
            when {
                Location.WORK isTransitioningTo Location.HOME -> tween(500)
                Location.CINEMA isTransitioningTo Location.HOME -> tween(4000)
                else -> tween(2000)
            }
        }
    ) {
        when (it) {
            Location.HOME -> homeOffset
            Location.WORK -> workOffset
            Location.CINEMA -> cinemaOffset
        }.toPersonOffset(smallSize, personSize)
    }


    BoxCanvas(
        canvasSize = canvasSize,
        smallSize = smallSize,
        personSize = personSize,
        workOffset = workOffset,
        homeOffset = homeOffset,
        cinemaOffset = cinemaOffset,
        personOffset = personOffset
    )
}

@Composable
private fun BoxCanvas(
    canvasSize: Int,
    smallSize: Int,
    personSize: Int,
    workOffset: IntOffset,
    homeOffset: IntOffset,
    cinemaOffset: IntOffset,
    personOffset: IntOffset
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(32.dp)
            .size(canvasSize.dp),
    ) {
        IconInCard(
            offset = workOffset,
            size = smallSize,
            imageVector = Icons.Default.Build
        )

        IconInCard(
            offset = cinemaOffset,
            size = smallSize,
            imageVector = Icons.Default.Star
        )

        IconInCard(
            offset = homeOffset,
            size = smallSize,
            imageVector = Icons.Default.Home,
        )
        IconInCard(
            offset = personOffset,
            size = personSize,
            padding = 2.dp,
            imageVector = Icons.Default.Person,
            colors = CardDefaults.cardColors(containerColor = Color(0xFF64B5F6))
        )
    }
}

@Composable
private fun IconInCard(
    offset: IntOffset,
    size: Int,
    padding: Dp = 8.dp,
    imageVector: ImageVector,
    tint: Color = Color.Black,
    colors: CardColors = CardDefaults.cardColors()
) {
    Card(
        modifier = Modifier
            .offset(
                x = offset.x.dp,
                y = offset.y.dp,
            )

            .size(size.dp),
        colors = colors
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentDescription = null,
            tint = tint,
            imageVector = imageVector
        )
    }
}


@Preview
@Composable
fun BoxCanvasPreview() {
    HomeWorkCinemaWithView()
}
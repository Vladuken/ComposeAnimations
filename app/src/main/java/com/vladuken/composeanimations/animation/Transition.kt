@file:OptIn(ExperimentalTransitionApi::class, ExperimentalAnimationApi::class)

package com.vladuken.composeanimations.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.createChildTransition
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class CameraState {
    Portrait,
    Camera,
    Video
}

@Composable
fun TransitionElement(modifier: Modifier = Modifier) {
    var state: CameraState by remember { mutableStateOf(CameraState.Camera) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        CameraComponent(
            modifier = Modifier.fillMaxSize(),
            state = state,
            itemSelected = { state = it }
        )
    }
}

@Composable
fun CameraComponent(
    modifier: Modifier = Modifier,
    state: CameraState,
    itemSelected: (CameraState) -> Unit,
) {
    val transition = updateTransition(
        label = "Parent Animation",
        targetState = state
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CameraIcon(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            transition = transition.createChildTransition {
                when (it) {
                    CameraState.Camera -> Icons.Default.AccountCircle
                    CameraState.Video -> Icons.Default.PlayArrow
                    CameraState.Portrait -> Icons.Default.Face
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        ZoomSelector(
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 8.dp
            ),
            transition = transition.createChildTransition {
                when (it) {
                    CameraState.Camera -> true
                    CameraState.Video -> true
                    CameraState.Portrait -> false
                }
            }
        )
        VideoSelector(
            modifier = Modifier.padding(bottom = 8.dp),
            transition = transition.createChildTransition {
                when (it) {
                    CameraState.Camera -> false
                    CameraState.Video -> true
                    CameraState.Portrait -> false
                }
            }
        )
        CameraBottomContainer(cameraButton = {
            CameraButton(
                transition = transition.createChildTransition {
                    when (it) {
                        CameraState.Camera -> 1f
                        CameraState.Video -> 0.6f
                        CameraState.Portrait -> 0.4f
                    }
                },
                transitionColor = transition.createChildTransition {
                    when (it) {
                        CameraState.Camera -> Color.White
                        CameraState.Video -> Color.Red
                        CameraState.Portrait -> Color.White
                    }
                }
            )
        },
            cameraModeSelector = {
                ModeSelector(
                    modes = CameraState.values().toList(),
                    selectedMode = state,
                    itemSelected = itemSelected
                )
            }
        )
    }
}

@Composable
private fun CameraBottomContainer(
    cameraButton: @Composable () -> Unit,
    cameraModeSelector: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(80.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            cameraButton()
        }
        Spacer(modifier = Modifier.height(8.dp))
        cameraModeSelector()
    }
}

@Composable
private fun CameraIcon(
    modifier: Modifier = Modifier,
    transition: Transition<ImageVector>
) {
    transition.AnimatedContent(
        transitionSpec = {
            slideInVertically() + fadeIn() + scaleIn() with
                    slideOutVertically() + fadeOut() + scaleOut()
        }
    ) {
        Icon(
            modifier = modifier,
            imageVector = it,
            contentDescription = null
        )
    }
}

@Composable
fun CameraButton(
    modifier: Modifier = Modifier,
    transition: Transition<Float>,
    transitionColor: Transition<Color>
) {
    val fraction by transition.animateFloat { it }
    val color by transitionColor.animateColor { it }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .aspectRatio(1f)
            .background(Color.White.copy(alpha = 0.3f))
            .border(2.dp, color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize(fraction)
                .background(color),
        )
    }
}

@Composable
fun ZoomSelector(
    modifier: Modifier = Modifier,
    transition: Transition<Boolean>
) {
    var selectedItem by remember { mutableStateOf("x1") }

    transition.AnimatedVisibility(
        modifier = modifier,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
        visible = { it }
    ) {
        SelectorHelper(
            items = listOf("x1", "x2"),
            selectedIte = selectedItem,
            itemSelected = { selectedItem = it }
        ) { text, isSelected ->
            SelectedText(isSelected, text)
        }
    }
}

@Composable
fun ModeSelector(
    modifier: Modifier = Modifier,
    modes: List<CameraState>,
    selectedMode: CameraState,
    itemSelected: (CameraState) -> Unit,
) {
    SelectorHelper(
        modifier = modifier,
        items = modes,
        selectedIte = selectedMode,
        itemSelected = { itemSelected(it) }
    ) { text, isSelected ->
        SelectedText(
            isSelected,
            when (text) {
                CameraState.Camera -> "Camera"
                CameraState.Video -> "Video"
                CameraState.Portrait -> "Portrait"
            }
        )
    }
}

@Composable
fun VideoSelector(
    modifier: Modifier = Modifier,
    transition: Transition<Boolean>
) {
    var selectedItem by remember { mutableStateOf("Normal") }
    transition.AnimatedVisibility(
        modifier = modifier,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
        visible = { it }
    ) {
        SelectorHelper(
            items = listOf("Slow Motion", "Normal", "Time Lapse"),
            selectedIte = selectedItem,
            itemSelected = { selectedItem = it }
        ) { text, isSelected ->
            SelectedText(isSelected, text)
        }
    }
}

@Composable
private fun SelectedText(isSelected: Boolean, text: String) {
    Text(
        text = text
    )
}

@Composable
fun <T : Any> SelectorHelper(
    modifier: Modifier = Modifier,
    items: List<T>,
    selectedIte: T,
    itemSelected: (T) -> Unit,
    content: @Composable (T, Boolean) -> Unit
) {
    val itemWithSelectedItem: List<Pair<T, Boolean>> = items.map {
        Pair(it, it == selectedIte)
    }
    Card(
        modifier = modifier
    ) {
        LazyRow(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(itemWithSelectedItem) { (item, isSelected) ->
                Card(
                    modifier = Modifier
                        .clickable { itemSelected(item) },
                    colors = CardDefaults.cardColors(
                        if (isSelected) {
                            MaterialTheme.colorScheme.background
                        } else {
                            Color.Transparent
                        }
                    )
                ) {
                    Box(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp,
                        )
                    ) {
                        content(
                            item,
                            isSelected
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransitionElementPreview() {
    TransitionElement()
}

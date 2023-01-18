package com.vladuken.composeanimations.testing

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vladuken.composeanimations.R

@Composable
fun Favourite(
    state: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onValueChanged: (Boolean) -> Unit,
    @DrawableRes favouriteVectorId: Int = R.drawable.ic_baseline_favorite_24,
    @DrawableRes nonFavouriteVectorId: Int = R.drawable.ic_baseline_favorite_border_24,
    tint: Color = Color.Red
) {
    Crossfade(
        modifier = modifier
            .size(48.dp)
            .padding(8.dp)
            .toggleable(value = state.value, onValueChange = onValueChanged),
        targetState = state.value
    ) { isFavourite ->
        if (isFavourite) {
            Icon(
                painter = painterResource(favouriteVectorId),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                tint = tint
            )
        } else {
            Icon(
                painter = painterResource(nonFavouriteVectorId),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                tint = tint
            )
        }
    }
}

@Preview
@Composable
fun Preview_Favourite() {
    val state = remember { mutableStateOf(false) }

    LaunchedEffect("LaunchAnimation") {
        state.value = true
    }
    Favourite(
        state = state,
        modifier = Modifier.size(100.dp),
        onValueChanged = { },
        tint = Color.Red
    )
}
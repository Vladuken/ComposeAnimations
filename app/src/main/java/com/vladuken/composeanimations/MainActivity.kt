package com.vladuken.composeanimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vladuken.composeanimations.api.core.AnimationSlot
import com.vladuken.composeanimations.api.samples.AnimatableSamplesScreen
import com.vladuken.composeanimations.api.samples.AnimateAsStateScreen
import com.vladuken.composeanimations.api.samples.AnimatedContentScreen
import com.vladuken.composeanimations.api.samples.AnimatedContentSizeScreen
import com.vladuken.composeanimations.api.samples.AnimatedVisibilityScreen
import com.vladuken.composeanimations.api.samples.AnimationSamplesScreen
import com.vladuken.composeanimations.ui.theme.ComposeAnimationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimationSamplesScreen(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 16.dp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun AnimationsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AnimationSlot(title = "AnimatedVisibility") {
            AnimatedVisibilityScreen()
        }
        AnimationSlot(title = "animate***AsState()") {
            AnimateAsStateScreen()
        }
        AnimationSlot(title = "AnimatedContent (Experimental)") {
            AnimatedContentScreen()
        }
        AnimationSlot(title = ".animateContentSize()") {
            AnimatedContentSizeScreen()
        }
        AnimationSlot(title = "Animatable Samples") {
            AnimatableSamplesScreen()
        }
        AnimationSlot(title = "Animation Samples") {
            AnimationSamplesScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimationsScreenPreview() {
    AnimationsScreen()
}

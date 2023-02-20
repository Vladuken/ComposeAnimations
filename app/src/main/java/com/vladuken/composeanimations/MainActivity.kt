package com.vladuken.composeanimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.vladuken.composeanimations.animation.AnimatableSamplesScreen
import com.vladuken.composeanimations.animation.AnimateAsStateScreen
import com.vladuken.composeanimations.animation.AnimatedContentScreen
import com.vladuken.composeanimations.animation.AnimatedContentSizeScreen
import com.vladuken.composeanimations.animation.AnimatedVisibilityScreen
import com.vladuken.composeanimations.animation.AnimationSamplesScreen
import com.vladuken.composeanimations.animation.AnimationSpecScreen
import com.vladuken.composeanimations.animation.AnimationSpot
import com.vladuken.composeanimations.animation.CameraButton
import com.vladuken.composeanimations.animation.TransitionElement
import com.vladuken.composeanimations.presentation.AnimateMultipleStates
import com.vladuken.composeanimations.presentation.AnimationPresentationSample
import com.vladuken.composeanimations.presentation.EncapsulateTransitionStates
import com.vladuken.composeanimations.presentation.HomeWorkCinemaWithView
import com.vladuken.composeanimations.presentation.InfiniteLineAnimation
import com.vladuken.composeanimations.presentation.StateFoo
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        AnimationPresentationSample()
                    }
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
        AnimationSpot(title = "AnimatedVisibility") {
            AnimatedVisibilityScreen()
        }
        AnimationSpot(title = "animate***AsState()") {
            AnimateAsStateScreen()
        }
        AnimationSpot(title = "AnimatedContent (Experimental)") {
            AnimatedContentScreen()
        }
        AnimationSpot(title = ".animateContentSize()") {
            AnimatedContentSizeScreen()
        }
        AnimationSpot(title = "Animatable Samples") {
            AnimatableSamplesScreen()
        }
        AnimationSpot(title = "Animation Samples") {
            AnimationSamplesScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimationsScreenPreview() {
    AnimationsScreen()
}

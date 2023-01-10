package com.vladuken.composeanimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vladuken.composeanimations.animation.AnimateAsStateScreen
import com.vladuken.composeanimations.animation.AnimatedVisibilityScreen
import com.vladuken.composeanimations.animation.AnimationSpot
import com.vladuken.composeanimations.ui.theme.ComposeAnimationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}

@Composable
fun AnimationsScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        AnimationSpot(title = "AnimatedVisibility") {
            AnimatedVisibilityScreen()
        }
        AnimationSpot(title = "animate***AsState()") {
            AnimateAsStateScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimationsScreenPreview() {
    AnimationsScreen()
}

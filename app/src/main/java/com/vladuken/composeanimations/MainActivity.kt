package com.vladuken.composeanimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vladuken.composeanimations.api.presentation.AnimationHeartSamplesScreen
import com.vladuken.composeanimations.api.presentation.CameraAnimationScreen
import com.vladuken.composeanimations.api.presentation.HomeWorkCinemaWithView
import com.vladuken.composeanimations.api.presentation.InfiniteLineAnimation
import com.vladuken.composeanimations.api.presentation.SizeColorRotationSquareScreen
import com.vladuken.composeanimations.api.samples.AnimatableSamplesScreen
import com.vladuken.composeanimations.api.samples.AnimateAsStateScreen
import com.vladuken.composeanimations.api.samples.AnimatedContentScreen
import com.vladuken.composeanimations.api.samples.AnimatedContentSizeScreen
import com.vladuken.composeanimations.api.samples.AnimatedVisibilityScreen
import com.vladuken.composeanimations.api.samples.AnimationSamplesScreen
import com.vladuken.composeanimations.api.samples.AnimationSpecScreen
import com.vladuken.composeanimations.ui.theme.ComposeAnimationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimationsScreen(
                        modifier = Modifier.padding(16.dp)
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
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "init"
    ) {

        // Complex Samples
        composable("8") { SizeColorRotationSquareScreen() }
        composable("9") { HomeWorkCinemaWithView() }
        composable("10") { AnimationHeartSamplesScreen() }
        composable("11") { InfiniteLineAnimation() }
        composable("12") { CameraAnimationScreen() }

        // API Samples
        composable("init") { ListOfAnimations(navController = navController) }
        composable("1") { AnimatedVisibilityScreen() }
        composable("2") { AnimationSpecScreen() }
        composable("3") { AnimateAsStateScreen() }
        composable("4") { AnimatedContentScreen() }
        composable("5") { AnimatedContentSizeScreen() }
        composable("6") { AnimatableSamplesScreen() }
        composable("7") { AnimationSamplesScreen() }
    }
}

@Composable
fun ListOfAnimations(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenTitle(title = "Complex Samples", isHeader = true)
        ScreenTitle(title = "SizeColorRotation Sample") { navController.navigate("8") }
        ScreenTitle(title = "Home Work Cinema Sample") { navController.navigate("9") }
        ScreenTitle(title = "Animation: Heart") { navController.navigate("10") }
        ScreenTitle(title = "Animatable: Infinite Line") { navController.navigate("11") }
        ScreenTitle(title = "Camera Animation") { navController.navigate("12") }

        ScreenTitle(title = "API Usage Samples", isHeader = true)
        ScreenTitle(title = "AnimatedVisibility") { navController.navigate("1") }
        ScreenTitle(title = "AnimationSpec") { navController.navigate("2") }
        ScreenTitle(title = "animate***AsState") { navController.navigate("3") }
        ScreenTitle(title = "AnimatedContent") { navController.navigate("4") }
        ScreenTitle(title = ".animateContentSize()") { navController.navigate("5") }
        ScreenTitle(title = "Animatable Samples") { navController.navigate("6") }
        ScreenTitle(title = "Animation Samples") { navController.navigate("7") }
    }
}

@Composable
private fun ScreenTitle(
    title: String,
    isHeader: Boolean = false,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = if (isHeader) {
            FontWeight.Bold
        } else {
            FontWeight.Normal
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AnimationsScreenPreview() {
    AnimationsScreen()
}

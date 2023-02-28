package com.vladuken.composeanimations

import androidx.compose.ui.test.junit4.createComposeRule
import com.karumi.shot.ScreenshotTest
import com.vladuken.composeanimations.testing.Particle
import org.junit.Rule
import org.junit.Test

class ParticleTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun animationShouldBeRenderedCorrectly() {
        composeTestRule.apply {
            setContent {
                mainClock.autoAdvance = false

                Particle()
            }

            compareScreenshot(composeTestRule, "state-0")

            mainClock.advanceTimeBy(200)
            compareScreenshot(composeTestRule, "state-1")

            mainClock.advanceTimeBy(1500)
            compareScreenshot(composeTestRule, "state-2")

            mainClock.advanceTimeBy(200)
            compareScreenshot(composeTestRule, "state-3")
        }
    }
}
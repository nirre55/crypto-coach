package com.example.cryptocoach.ui.screen

import androidx.compose.ui.platform.LocalContext
import org.junit.Rule
import org.junit.Test
import com.example.cryptocoach.utils.TestTags
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cryptocoach.ui.screens.PatternScreen

class PatternInfoBaseScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun patternTitle_isVisible() {
        lateinit var navController: TestNavHostController

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            PatternScreen(navController = navController)
        }

        composeTestRule.onNodeWithTag(TestTags.PATTERN_TITLE).assertExists()
    }
}
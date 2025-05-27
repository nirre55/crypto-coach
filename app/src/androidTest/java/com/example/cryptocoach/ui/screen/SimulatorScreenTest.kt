package com.example.cryptocoach.ui.screen

import org.junit.Rule
import org.junit.Test
import com.example.cryptocoach.utils.TestTags
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.cryptocoach.ui.screens.SimulatorScreen

class SimulatorScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun simulatorTitle_isVisible() {
        composeTestRule.setContent {
            SimulatorScreen()
        }
        composeTestRule.onNodeWithTag(TestTags.SIMULATOR_TITLE).assertExists()
    }
}
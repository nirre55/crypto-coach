package com.example.cryptocoach.ui.screen

import org.junit.Rule
import org.junit.Test
import com.example.cryptocoach.utils.TestTags
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.cryptocoach.ui.screens.SettingsScreen

class SettingsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun settingsTitle_isVisible() {
        composeTestRule.setContent {
            SettingsScreen()
        }
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_TITLE).assertExists()
    }
}
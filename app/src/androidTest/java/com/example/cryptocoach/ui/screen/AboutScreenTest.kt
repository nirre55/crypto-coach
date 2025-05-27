package com.example.cryptocoach.ui.screen

import org.junit.Rule
import org.junit.Test
import com.example.cryptocoach.utils.TestTags
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.cryptocoach.ui.screens.AboutScreen

class AboutScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun aboutTitle_isVisible() {
        composeTestRule.setContent {
            AboutScreen()
        }
        composeTestRule.onNodeWithTag(TestTags.ABOUT_TITLE).assertExists()
    }
}
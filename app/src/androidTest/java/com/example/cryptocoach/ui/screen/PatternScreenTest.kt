package com.example.cryptocoach.ui.screen

import org.junit.Rule
import org.junit.Test
import com.example.cryptocoach.utils.TestTags
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.cryptocoach.ui.screens.PatternScreen

class PatternScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun patternTitle_isVisible() {
        composeTestRule.setContent {
            PatternScreen()
        }
        composeTestRule.onNodeWithTag(TestTags.PATTERN_TITLE).assertExists()
    }
}
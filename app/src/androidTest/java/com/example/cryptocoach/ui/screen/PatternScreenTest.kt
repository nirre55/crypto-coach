package com.example.cryptocoach.ui.screen

import com.example.cryptocoach.MainActivity
import org.junit.Rule
import org.junit.Test
import com.example.cryptocoach.utils.TestTags
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag

class PatternScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun patternTitle_isVisible() {
        composeTestRule.onNodeWithTag(TestTags.PATTERN_TITLE).assertExists()
    }
}
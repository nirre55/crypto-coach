package com.example.cryptocoach.ui.screen

import org.junit.Rule
import org.junit.Test
import com.example.cryptocoach.utils.TestTags
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.cryptocoach.ui.screens.EducationScreen

class EducationScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun educationTitle_isVisible() {
        composeTestRule.setContent {
            EducationScreen()
        }
        composeTestRule.onNodeWithTag(TestTags.EDUCATION_TITLE).assertExists()
    }
}
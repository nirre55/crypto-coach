package com.example.cryptocoach.ui.screen

import com.example.cryptocoach.MainActivity
import org.junit.Rule
import org.junit.Test
import com.example.cryptocoach.utils.TestTags
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag

class DashboardScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun dashboardTitle_isVisible() {
        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_TITLE).assertExists()
    }
}
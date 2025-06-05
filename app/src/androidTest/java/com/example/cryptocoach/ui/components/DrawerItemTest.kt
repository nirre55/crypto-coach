package com.example.cryptocoach.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cryptocoach.navigation.Screen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DrawerItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun drawerItem_unselected_displaysLabelAndIcon_and_clicks() {
        var clicked = false

        composeTestRule.setContent {
            MaterialTheme {
                DrawerItem(
                    screen = Screen.Education,
                    currentRoute = null,
                    onClick = { clicked = true },
                    testTag = "drawer_edu"
                )
            }
        }

        // Présence du tag et du label
        composeTestRule.onNodeWithTag("drawer_edu").assertExists()
        composeTestRule.onNodeWithText("Education").assertExists()

        // Clic déclenche le callback
        composeTestRule.onNodeWithTag("drawer_edu").performClick()
        assertTrue(clicked)
    }

    @Test
    fun drawerItem_selected_remainsClickable() {
        var clicked = false

        composeTestRule.setContent {
            MaterialTheme {
                DrawerItem(
                    screen = Screen.Simulator,
                    currentRoute = Screen.Simulator.route,
                    onClick = { clicked = true },
                    testTag = "drawer_sim"
                )
            }
        }

        // Le nœud est cliquable même s'il est sélectionné
        composeTestRule.onNodeWithTag("drawer_sim")
            .assertExists()
            .assert(hasClickAction())

        // Vérification du clic
        composeTestRule.onNodeWithTag("drawer_sim").performClick()
        assertTrue(clicked)
    }
}

package com.example.cryptocoach.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.cryptocoach.navigation.Screen
import org.junit.Rule
import org.junit.Test

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
        assert(clicked)
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
        assert(clicked)
    }
}

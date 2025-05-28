package com.example.cryptocoach.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import androidx.compose.foundation.layout.*


class PreferenceItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun preferenceItem_displaysTitleAndValue_and_isClickable() {
        var clicked = false

        composeTestRule.setContent {
            MaterialTheme {
                PreferenceItem(
                    title = "Mon titre",
                    currentValue = "Ma valeur",
                    onClick = { clicked = true },
                    testTag = "pref_item"
                )
            }
        }

        // Le Row existe et est cliquable
        composeTestRule.onNodeWithTag("pref_item")
            .assertExists()
            .assert(hasClickAction())

        // Le titre et la valeur sont affichés
        composeTestRule.onNodeWithText("Mon titre").assertExists()
        composeTestRule.onNodeWithText("Ma valeur").assertExists()

        // Exécute le clic et vérifie l'appel du callback
        composeTestRule.onNodeWithTag("pref_item").performClick()
        assert(clicked)
    }

    @Test
    fun preferenceItem_multipleInstances_areDistinctlyTaggable() {
        composeTestRule.setContent {
            MaterialTheme {
                Column {
                    PreferenceItem(
                        title = "T1",
                        currentValue = "V1",
                        onClick = {},
                        testTag = "item_1"
                    )
                    PreferenceItem(
                        title = "T2",
                        currentValue = "V2",
                        onClick = {},
                        testTag = "item_2"
                    )
                }
            }
        }

        // Vérifie que chaque instance est repérée par son tag
        composeTestRule.onNodeWithTag("item_1").assertExists()
        composeTestRule.onNodeWithText("T1").assertExists()
        composeTestRule.onNodeWithTag("item_2").assertExists()
        composeTestRule.onNodeWithText("T2").assertExists()
    }
}

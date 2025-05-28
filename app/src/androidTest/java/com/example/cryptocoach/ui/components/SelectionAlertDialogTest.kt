package com.example.cryptocoach.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class SelectionAlertDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val options = listOf("One" to "1", "Two" to "2")

    @Test
    fun dialog_notShown_whenFlagFalse() {
        composeTestRule.setContent {
            SelectionAlertDialog(
                showDialog = false,
                onDismissRequest = {},
                title = "TestTitle",
                options = options,
                currentValue = "1",
                onOptionSelected = {},
                testTagPrefix = "dlg"
            )
        }

        // Ni le titre, ni aucune option, ni le bouton Cancel
        composeTestRule.onNodeWithText("TestTitle").assertDoesNotExist()
        composeTestRule.onNodeWithTag("dlg_option_1").assertDoesNotExist()
        composeTestRule.onNodeWithText("Cancel").assertDoesNotExist()
    }

    @Test
    fun dialog_showsOptions_and_selectsOption() {
        var selected: String? = null
        var dismissed = false

        composeTestRule.setContent {
            var show by remember { mutableStateOf(true) }
            Box {
                SelectionAlertDialog(
                    showDialog = show,
                    onDismissRequest = { dismissed = true; show = false },
                    title = "Choose",
                    options = options,
                    currentValue = "1",
                    onOptionSelected = { selected = it },
                    testTagPrefix = "dlg"
                )
            }
        }

        // Le titre est affiché
        composeTestRule.onNodeWithText("Choose").assertExists()

        // Attendre que l'option "Two" apparaisse
        composeTestRule.waitUntil(timeoutMillis = 2_000) {
            composeTestRule.onAllNodesWithTag("dlg_option_2").fetchSemanticsNodes().isNotEmpty()
        }

        // Cliquer sur l'option "Two"
        composeTestRule.onNodeWithTag("dlg_option_2").performClick()

        // Vérifie qu'on a bien sélectionné "2" et que le dialog s'est fermé
        assert(selected == "2")
        assert(dismissed)
    }

    @Test
    fun dialog_cancelButton_callsDismiss() {
        var dismissed = false

        composeTestRule.setContent {
            Box {
                SelectionAlertDialog(
                    showDialog = true,
                    onDismissRequest = { dismissed = true },
                    title = "CancelTest",
                    options = options,
                    currentValue = "1",
                    onOptionSelected = {},
                    testTagPrefix = "dlg"
                )
            }
        }

        // Clique sur le bouton Cancel
        composeTestRule.onNodeWithText("Cancel").performClick()
        assert(dismissed)
    }
}

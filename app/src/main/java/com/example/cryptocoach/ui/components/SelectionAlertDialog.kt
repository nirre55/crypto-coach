package com.example.cryptocoach.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cryptocoach.R

@Composable
fun SelectionAlertDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    options: List<Pair<String, String>>,
    currentValue: String,
    onOptionSelected: (String) -> Unit,
    testTagPrefix: String
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(title) },
            text = {
                Column {
                    options.forEach { (displayName, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onOptionSelected(value)
                                    onDismissRequest()
                                }
                                .padding(vertical = 12.dp)
                                .testTag("${testTagPrefix}_option_$value"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = value == currentValue,
                                onClick = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(displayName)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

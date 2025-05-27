package com.example.cryptocoach.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.cryptocoach.navigation.Screen


@Composable
fun DrawerItem(
    screen: Screen,
    currentRoute: String?,
    onClick: () -> Unit,
    testTag: String? = null
) {
    val selected = currentRoute == screen.route

    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = null,
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        },
        label = { Text(stringResource(screen.titleRes)) },
        selected = selected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.primary
        ),
        modifier = testTag?.let { Modifier.testTag(it) } ?: Modifier
    )
}

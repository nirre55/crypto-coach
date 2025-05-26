package com.example.cryptocoach.ui.screens
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.testTag
import com.example.cryptocoach.R
import com.example.cryptocoach.utils.TestTags

@Composable
fun AboutScreen() {
    Text(
        text = stringResource(R.string.about),
        modifier = Modifier
            .padding(16.dp)
            .testTag(TestTags.ABOUT_TITLE))
}
package com.example.cryptocoach.ui.screens
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import com.example.cryptocoach.R

@Composable
fun PatternScreen() {
    Text(text = stringResource(R.string.patterns), modifier = Modifier.padding(16.dp))
}

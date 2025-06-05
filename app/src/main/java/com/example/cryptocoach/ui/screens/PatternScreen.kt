package com.example.cryptocoach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cryptocoach.R
import com.example.cryptocoach.ui.components.*
import com.example.cryptocoach.utils.TestTags

@Composable
fun PatternScreen() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.patterns),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .testTag(TestTags.PATTERN_TITLE)
        )

        PatternCard(
            title = stringResource(R.string.bullish_candlesticks),
            description = stringResource(R.string.desc_bullish_candlesticks),
            illustration = { BullishCandles() },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        PatternCard(
            title = stringResource(R.string.bearish_candlesticks),
            description = stringResource(R.string.desc_bearish_candlesticks),
            illustration = { BearishCandles() },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        PatternCard(
            title = stringResource(R.string.bullish_patterns),
            description = stringResource(R.string.desc_bullish_patterns),
            illustration = { BullishPattern() },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        PatternCard(
            title = stringResource(R.string.bearish_patterns),
            description = stringResource(R.string.desc_bearish_patterns),
            illustration = { BearishPattern() },
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

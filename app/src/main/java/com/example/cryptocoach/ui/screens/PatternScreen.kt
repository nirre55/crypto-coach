package com.example.cryptocoach.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
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
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = stringResource(R.string.patterns),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .testTag(TestTags.PATTERN_TITLE)
            )
        }

        item {
            PatternCard(
                title = stringResource(R.string.bullish_candlesticks),
                description = stringResource(R.string.desc_bullish_candlesticks),
                illustration = { BullishCandles() }
            )
        }

        item {
            PatternCard(
                title = stringResource(R.string.bearish_candlesticks),
                description = stringResource(R.string.desc_bearish_candlesticks),
                illustration = { BearishCandles() }
            )
        }

        item {
            PatternCard(
                title = stringResource(R.string.bullish_patterns),
                description = stringResource(R.string.desc_bullish_patterns),
                illustration = { BullishPattern() }
            )
        }

        item {
            PatternCard(
                title = stringResource(R.string.bearish_patterns),
                description = stringResource(R.string.desc_bearish_patterns),
                illustration = { BearishPattern() }
            )
        }
    }
}

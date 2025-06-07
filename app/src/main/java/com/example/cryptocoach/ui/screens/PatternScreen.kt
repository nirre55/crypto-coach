package com.example.cryptocoach.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptocoach.R
import com.example.cryptocoach.ui.components.PatternCard
import com.example.cryptocoach.ui.theme.TradingColors

@Preview(showBackground = true)
@Composable
fun PatternScreen() {
    // 1) Lit les couleurs DANS la portée @Composable
    val bullishColor = TradingColors.Bullish
    val bearishColor = TradingColors.Bearish

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = stringResource(R.string.patterns),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Start
            )
        }

        val list = listOf(
            Triple(
                R.drawable.bullish_candlesticks,
                R.string.bullish_candlesticks,
                bullishColor
            ),
            Triple(
                R.drawable.bearish_candlesticks,
                R.string.bearish_candlesticks,
                bearishColor
            ),
            Triple(
                R.drawable.bullish_patterns,
                R.string.bullish_patterns,
                bullishColor
            ),
            Triple(
                R.drawable.bearish_patterns,
                R.string.bearish_patterns,
                bearishColor
            )
        )

        list.forEach { (icon, titleRes, accent) ->
            item {
                PatternCard(
                    title       = stringResource(titleRes),
                    description = stringResource(
                        when (titleRes) {
                            R.string.bullish_candlesticks -> R.string.desc_bullish_candlesticks
                            R.string.bearish_candlesticks -> R.string.desc_bearish_candlesticks
                            R.string.bullish_patterns    -> R.string.desc_bullish_patterns
                            else                          -> R.string.desc_bearish_patterns
                        }
                    ),
                    imageVector = ImageVector.vectorResource(icon),
                    accentColor = accent
                )
            }
        }
    }
}

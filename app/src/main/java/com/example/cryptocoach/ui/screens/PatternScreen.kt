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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cryptocoach.R
import com.example.cryptocoach.model.PatternInfoBase
import com.example.cryptocoach.model.PatternTypeBase
import com.example.cryptocoach.navigation.Screen
import com.example.cryptocoach.ui.components.PatternCardBase
import com.example.cryptocoach.ui.theme.TradingColors

@Composable
fun PatternScreen(navController: NavHostController) {
    // 1) Lit les couleurs DANS la portée @Composable
    val bullishColor = TradingColors.Bullish
    val bearishColor = TradingColors.Bearish
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
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

        val list = PatternInfoBase.entries.toTypedArray()

        list.forEach { pattern ->
            item {
                PatternCardBase(
                    title       = stringResource(pattern.titleRes),
                    description = stringResource(pattern.descriptionRes),
                    imageVector = ImageVector.vectorResource(pattern.iconRes),
                    accentColor = when(pattern.type) {
                        PatternTypeBase.BULLISH -> bullishColor
                        PatternTypeBase.BEARISH -> bearishColor
                    },
                    onClick = {
                        // 🔁 Appelle la navigation ici
                        navController.navigate(Screen.PatternList.routeFor(pattern.group))
                    }
                )
            }
        }
    }
}

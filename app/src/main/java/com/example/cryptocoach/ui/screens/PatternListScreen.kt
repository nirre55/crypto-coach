package com.example.cryptocoach.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.cryptocoach.model.PatternGroup
import com.example.cryptocoach.repository.PatternRepository
import com.example.cryptocoach.ui.components.PatternCardBase

@Composable
fun PatternListScreen(
    group: PatternGroup,
    repository: PatternRepository,
    onPatternClick: (String) -> Unit
) {
    val patterns = remember { repository.getPatternsByGroup(group) }

    LazyColumn {
        patterns.forEach { pattern ->
            item {
                PatternCardBase(pattern = pattern) {
                    onPatternClick(pattern.id)
                }
            }
        }
    }
}
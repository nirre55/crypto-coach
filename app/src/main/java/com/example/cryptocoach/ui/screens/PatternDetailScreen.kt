package com.example.cryptocoach.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptocoach.repository.PatternRepository
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@Composable
fun PatternDetailScreen(
    patternId: String,
    repository: PatternRepository
) {
    val pattern = remember { repository.getPatternById(patternId) }

    pattern?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = ImageVector.vectorResource(it.iconRes),
                contentDescription = null,
                modifier = Modifier.size(96.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(it.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(it.description, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

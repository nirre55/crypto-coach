package com.example.cryptocoach.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun BoxScope.BullishCandles(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(modifier.matchParentSize()) {
        val candleWidth = size.width / 7f
        val gap = candleWidth
        val heights = listOf(0.3f, 0.5f, 0.7f, 0.9f)
        heights.forEachIndexed { index, height ->
            val x = index * (candleWidth + gap)
            drawRect(
                color = color,
                topLeft = Offset(x, size.height * (1f - height)),
                size = Size(candleWidth, size.height * height)
            )
        }
    }
    Icon(
        imageVector = Icons.Filled.ArrowUpward,
        contentDescription = null,
        tint = color,
        modifier = Modifier.align(androidx.compose.ui.Alignment.TopEnd)
    )
}

@Composable
fun BoxScope.BearishCandles(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.error
    Canvas(modifier.matchParentSize()) {
        val candleWidth = size.width / 7f
        val gap = candleWidth
        val heights = listOf(0.9f, 0.7f, 0.5f, 0.3f)
        heights.forEachIndexed { index, height ->
            val x = index * (candleWidth + gap)
            drawRect(
                color = color,
                topLeft = Offset(x, size.height * (1f - height)),
                size = Size(candleWidth, size.height * height)
            )
        }
    }
    Icon(
        imageVector = Icons.Filled.ArrowDownward,
        contentDescription = null,
        tint = color,
        modifier = Modifier.align(androidx.compose.ui.Alignment.TopEnd)
    )
}

@Composable
fun BoxScope.BullishPattern(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(modifier.matchParentSize()) {
        val path = Path().apply {
            moveTo(0f, size.height * 0.8f)
            lineTo(size.width * 0.25f, size.height * 0.4f)
            lineTo(size.width * 0.5f, size.height * 0.8f)
            lineTo(size.width * 0.75f, size.height * 0.4f)
            lineTo(size.width, size.height * 0.8f)
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = size.width * 0.05f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
    Icon(
        imageVector = Icons.Filled.ArrowUpward,
        contentDescription = null,
        tint = color,
        modifier = Modifier.align(androidx.compose.ui.Alignment.TopEnd)
    )
}

@Composable
fun BoxScope.BearishPattern(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.error
    Canvas(modifier.matchParentSize()) {
        val path = Path().apply {
            moveTo(0f, size.height * 0.2f)
            lineTo(size.width * 0.25f, size.height * 0.6f)
            lineTo(size.width * 0.5f, size.height * 0.2f)
            lineTo(size.width * 0.75f, size.height * 0.6f)
            lineTo(size.width, size.height * 0.2f)
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = size.width * 0.05f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
    Icon(
        imageVector = Icons.Filled.ArrowDownward,
        contentDescription = null,
        tint = color,
        modifier = Modifier.align(androidx.compose.ui.Alignment.TopEnd)
    )
}


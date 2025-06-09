package com.example.cryptocoach.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.cryptocoach.R

enum class PatternTypeBase {
    BULLISH,
    BEARISH
}

enum class PatternInfoBase(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val iconRes: Int,
    val type: PatternTypeBase,
    val group: PatternGroup
) {
    BULLISH_CANDLESTICKS(
        titleRes = R.string.bullish_candlesticks,
        descriptionRes = R.string.desc_bullish_candlesticks,
        iconRes = R.drawable.bullish_candlesticks,
        type = PatternTypeBase.BULLISH,
        group = PatternGroup.BULLISH_CANDLESTICKS
    ),
    BEARISH_CANDLESTICKS(
        titleRes = R.string.bearish_candlesticks,
        descriptionRes = R.string.desc_bearish_candlesticks,
        iconRes = R.drawable.bearish_candlesticks,
        type = PatternTypeBase.BEARISH,
        group = PatternGroup.BEARISH_CANDLESTICKS
    ),
    BULLISH_PATTERNS(
        titleRes = R.string.bullish_patterns,
        descriptionRes = R.string.desc_bullish_patterns,
        iconRes = R.drawable.bullish_patterns,
        type = PatternTypeBase.BULLISH,
        group = PatternGroup.BULLISH_PATTERNS
    ),
    BEARISH_PATTERNS(
        titleRes = R.string.bearish_patterns,
        descriptionRes = R.string.desc_bearish_patterns,
        iconRes = R.drawable.bearish_patterns,
        type = PatternTypeBase.BEARISH,
        group = PatternGroup.BEARISH_PATTERNS
    );
} 
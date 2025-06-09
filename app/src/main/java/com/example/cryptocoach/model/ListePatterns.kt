package com.example.cryptocoach.model

object ListePatterns {
    val patterns = listOf(
        Pattern(
            id = "bullish_engulfing",
            name = "Bullish Engulfing",
            iconRes = PatternIcons.unknownIcone,
            description = "Candle closes above previous red body. Sign of reversal.",
            group = PatternGroup.BULLISH_CANDLESTICKS
        ),
        Pattern(
            id = "bearish_engulfing",
            name = "Bearish Engulfing",
            iconRes = PatternIcons.unknownIcone,
            description = "Candle closes below previous green body. Sign of reversal.",
            group = PatternGroup.BEARISH_CANDLESTICKS
        ),
        Pattern(
            id = "double_bottom",
            name = "Double Bottom",
            iconRes = PatternIcons.unknownIcone,
            description = "W-shaped pattern signaling a bullish reversal.",
            group = PatternGroup.BULLISH_PATTERNS
        ),
        Pattern(
            id = "doji",
            name = "Doji",
            iconRes = PatternIcons.unknownIcone,
            description = "doji candlesticks signaling a bullish reversal.",
            group = PatternGroup.BULLISH_CANDLESTICKS
        ),
        Pattern(
            id = "double_top",
            name = "Double Top",
            iconRes = PatternIcons.unknownIcone,
            description = "M-shaped pattern signaling a bearich reversal.",
            group = PatternGroup.BEARISH_PATTERNS
        )
    )
}


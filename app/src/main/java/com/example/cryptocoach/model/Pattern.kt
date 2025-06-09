package com.example.cryptocoach.model

import androidx.annotation.DrawableRes

data class Pattern(
    val id: String,
    val name: String,
    @DrawableRes val iconRes: Int,
    val description: String,
    val group: PatternGroup
)
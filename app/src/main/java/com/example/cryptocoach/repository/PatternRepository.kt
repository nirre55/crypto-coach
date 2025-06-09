package com.example.cryptocoach.repository

import com.example.cryptocoach.model.Pattern
import com.example.cryptocoach.model.PatternGroup


interface PatternRepository {
    fun getPatternById(id: String): Pattern?
    fun getPatternsByGroup(group: PatternGroup): List<Pattern>
}
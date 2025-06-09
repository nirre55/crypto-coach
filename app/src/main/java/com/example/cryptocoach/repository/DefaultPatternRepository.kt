package com.example.cryptocoach.repository

import com.example.cryptocoach.model.ListePatterns
import com.example.cryptocoach.model.Pattern
import com.example.cryptocoach.model.PatternGroup


class DefaultPatternRepository : PatternRepository {
    private val patterns = ListePatterns.patterns

    override fun getPatternById(id: String): Pattern? {
        return patterns.find { it.id == id }
    }

    override fun getPatternsByGroup(group: PatternGroup): List<Pattern> {
        return patterns.filter { it.group == group }
    }
}
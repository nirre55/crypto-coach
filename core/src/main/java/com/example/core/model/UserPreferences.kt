package com.example.core.model

/**
 * Options possibles pour le thème.
 * - LIGHT  → mode clair
 * - DARK   → mode sombre
 * - SYSTEM → suivre le thème système
 */
enum class ThemeOption(val storageValue: String) {
    LIGHT("Light"),
    DARK("Dark"),
    SYSTEM("System");

    companion object {
        /** Convertit la valeur stockée en chaîne (DataStore) en ThemeOption */
        fun fromStorage(value: String): ThemeOption =
            entries.find { it.storageValue == value } ?: SYSTEM
    }
}

/**
 * Options possibles pour la langue.
 * - ENGLISH → anglais
 * - FRENCH  → français
 * - SYSTEM  → suivre la langue système
 */
enum class LanguageOption(val tag: String) {
    ENGLISH("en"),
    FRENCH("fr"),
    SYSTEM("system");

    companion object {
        /** Convertit la valeur stockée en chaîne (DataStore) en LanguageOption */
        fun fromStorage(value: String): LanguageOption =
            entries.find { it.tag == value } ?: SYSTEM
    }
}

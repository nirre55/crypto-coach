package com.example.cryptocoach.navigation
import org.junit.Test

import org.junit.Assert.*
import com.example.cryptocoach.R

class NavigationItemsTest {
    @Test
    fun screenRoutes_areCorrect() {
        assertEquals("dashboard", Screen.Dashboard.route)
        assertEquals("education", Screen.Education.route)
        assertEquals("patterns", Screen.Patterns.route)
        assertEquals("simulator", Screen.Simulator.route)
        assertEquals("settings", Screen.Settings.route)
        assertEquals("about", Screen.About.route)
    }

    @Test
    fun screenTitleResources_areCorrect() {
        assertEquals(R.string.dashboard, Screen.Dashboard.titleRes)
        assertEquals(R.string.education, Screen.Education.titleRes)
        assertEquals(R.string.patterns, Screen.Patterns.titleRes)
        assertEquals(R.string.simulator, Screen.Simulator.titleRes)
        assertEquals(R.string.settings, Screen.Settings.titleRes)
        assertEquals(R.string.about, Screen.About.titleRes)
    }
}
// app/src/androidTest/java/com/example/cryptocoach/HiltSmokeTest.kt
package com.example.cryptocoach

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import org.junit.Assert.assertEquals
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Dépendance simple pour le test
class TestDependency {
    fun getMessage() = "Hilt is working!"
}

// Module Hilt pour fournir la dépendance
@Module
@InstallIn(SingletonComponent::class)
object TestModule {
    @Provides
    @Singleton
    fun provideTestDependency(): TestDependency {
        return TestDependency()
    }
}


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HiltSmokeTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testDependency: TestDependency

    @Test
    fun testHiltInjection() {
        // Injecte les dépendances
        hiltRule.inject()

        // Vérifie que la dépendance est correctement injectée
        assertEquals("Hilt is working!", testDependency.getMessage())
    }
}

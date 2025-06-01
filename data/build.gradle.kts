plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt) // Ensure kotlin-kapt is present
    alias(libs.plugins.hilt)       // Add Hilt plugin
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}
dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core.android)

    // Add Hilt dependencies
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
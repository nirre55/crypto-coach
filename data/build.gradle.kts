plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("com.google.devtools.ksp") // Apply KSP plugin directly
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
    ksp(libs.hilt.compiler)
}
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    id("com.google.devtools.ksp")
    id("com.google.firebase.firebase-perf")

}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

val webClientId: String = localProperties.getProperty("WEB_CLIENT_ID", "")

android {
    namespace = "com.example.fugitive"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fugitive"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "WEB_CLIENT_ID", "\"$webClientId\"") // ✅ Add to BuildConfig
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "WEB_CLIENT_ID", "\"$webClientId\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}
dependencies {
    // Core Android & Jetpack Components
    implementation(libs.androidx.core.ktx) // Core KTX for Android utilities
    implementation(libs.androidx.lifecycle.runtime.ktx) // Lifecycle management
    implementation(libs.androidx.activity.compose) // Activity support for Compose
    implementation(platform(libs.androidx.compose.bom)) // Compose BOM for version alignment

    // Jetpack Compose UI
    implementation(libs.androidx.ui) // Core UI components
    implementation(libs.androidx.ui.graphics) // Graphics utilities for UI
    implementation(libs.androidx.ui.tooling.preview) // Preview support in Compose
    implementation(libs.androidx.material3) // Material Design 3 components
    implementation(libs.ui) // Additional UI components

    // Navigation
    implementation(libs.androidx.navigation.runtime.ktx) // Navigation runtime support
    implementation(libs.androidx.navigation.compose) // Jetpack Compose navigation

    // Firebase Services
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx) // Firestore database integration
    implementation(libs.firebase.storage.ktx) // Firebase Storage for EPUB files

    // Fonts & Styling
    implementation(libs.androidx.ui.text.google.fonts) // Google Fonts integration
    implementation(libs.androidx.ui.text.google.fonts.v178) // Specific version of Google Fonts
    implementation(libs.accompanist.flowlayout) // Accompanist Flow Layout for Compose

    // Coroutines for Asynchronous Tasks
    implementation(libs.kotlinx.coroutines.android) // Kotlin coroutines for background operations

    // Local Storage (Optional)
    implementation(libs.androidx.room.runtime) // Room Database for local storage

    // Testing Dependencies
    testImplementation(libs.junit) // JUnit for unit testing
    androidTestImplementation(libs.androidx.junit) // Android JUnit support
    androidTestImplementation(libs.androidx.espresso.core) // Espresso UI testing
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Compose BOM for tests
    androidTestImplementation(libs.androidx.ui.test.junit4) // UI testing in Compose

    // Debugging Tools
    debugImplementation(libs.androidx.ui.tooling) // Compose UI tool for preview/debugging
    debugImplementation(libs.androidx.ui.test.manifest) // Manifest for UI testing

    // Miscellaneous
    implementation(libs.androidx.tools.core) // Core tools (unspecified usage, verify necessity)
    implementation(libs.glide)
    implementation(libs.coil.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.accompanist.placeholder)
    implementation(libs.koin.android) // Koin for Android
    implementation(libs.koin.androidx.compose) // Koin for Jetpack Compose

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.google.accompanist.navigation.animation)
    implementation(platform(libs.firebase.bom))

    // Add the dependency for the Performance Monitoring library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebase.perf)

    implementation(libs.play.services.auth)


}



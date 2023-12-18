plugins {
    id("dev.rsierov.android.application")
    id("dev.rsierov.kotlin.android")
    id("dev.rsierov.compose.multiplatform")
    alias(libs.plugins.google.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.rsierov.rijks"
    defaultConfig {
        testInstrumentationRunner = "dev.rsierov.rijks.HiltApplicationTestRunner"
    }
}

dependencies {

    implementation(projects.api.public)
    implementation(projects.core.screen)
    implementation(projects.feature.artGallery)
    implementation(projects.feature.artDetails)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)
    
    debugImplementation(libs.androidx.compose.testManifest)
    androidTestImplementation(projects.data.testFixtures)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.uiautomator)
    androidTestImplementation(libs.androidx.compose.test)
    androidTestImplementation(libs.okhttp.mockwebserver)
    androidTestImplementation(libs.google.dagger.hilt.testing.android)
    kspAndroidTest(libs.google.dagger.hilt.testing.compiler)
}
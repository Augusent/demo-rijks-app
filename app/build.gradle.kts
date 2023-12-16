plugins {
    id("dev.rsierov.android.application")
    id("dev.rsierov.kotlin.android")
    id("dev.rsierov.compose.multiplatform")
    alias(libs.plugins.google.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.rsierov.rijks"
}

dependencies {

    implementation(projects.api.public)
    implementation(projects.api.rijksImpl) // TODO: temporary, remove

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(compose.runtime)
    implementation(compose.ui)
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)
    
    androidTestImplementation(libs.androidx.test.junit)
}
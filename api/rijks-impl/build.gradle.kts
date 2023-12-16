plugins {
    id("dev.rsierov.android.library")
    id("dev.rsierov.kotlin.android")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.rsierov.api.rijks"
}

dependencies {
    implementation(projects.api.public)
    implementation(libs.kotlinx.serialization)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.negotiation)
    implementation(libs.ktor.client.serialization)
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)
}
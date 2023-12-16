plugins {
    id("dev.rsierov.android.library")
    id("dev.rsierov.kotlin.android")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.rsierov.api"
}

dependencies {
    implementation(projects.domain.model)
    implementation(libs.kotlinx.serialization)
}
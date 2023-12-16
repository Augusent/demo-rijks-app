plugins {
    id("dev.rsierov.android.library")
    id("dev.rsierov.kotlin.android")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.rsierov.domain.model"
}

dependencies {
    implementation(libs.kotlinx.serialization)
}
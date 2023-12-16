plugins {
    id("dev.rsierov.android.library")
    id("dev.rsierov.kotlin.android")
    id("dev.rsierov.compose.multiplatform")
}

android {
    namespace = "dev.rsierov.core.screen"
}

dependencies {
    implementation(compose.foundation)
    implementation(compose.runtime)
}
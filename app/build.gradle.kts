plugins {
    id("dev.rsierov.android.application")
    id("dev.rsierov.kotlin.android")
    id("dev.rsierov.compose.multiplatform")
}

android {
    namespace = "dev.rsierov.rijks"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(compose.runtime)
    implementation(compose.ui)
    androidTestImplementation(libs.androidx.test.junit)
}
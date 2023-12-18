plugins {
    id("dev.rsierov.android.library")
    id("dev.rsierov.kotlin.android")
}

android {
    namespace = "dev.rsierov.data.fake"
}

dependencies {
    api(projects.data)
    api(projects.api.public)
    api(libs.androidx.paging.testing)
    implementation(libs.androidx.paging.common)
    implementation(libs.kotlin.coroutines.core)
}
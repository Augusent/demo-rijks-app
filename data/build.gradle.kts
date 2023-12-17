plugins {
    id("dev.rsierov.android.library")
    id("dev.rsierov.kotlin.android")
}

android {
    namespace = "dev.rsierov.data"
}

dependencies {
    api(projects.domain.model)
    implementation(projects.api.public)
    implementation(projects.api.rijksImpl)
    implementation(libs.androidx.paging.common)
    implementation(libs.misc.javax.inject)
}
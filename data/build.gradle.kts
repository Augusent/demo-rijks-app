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
    testImplementation(projects.data.testFixtures)
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(kotlin("test"))
}
plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "dev.rsierov.android.application"
            implementationClass = "dev.rsierov.gradle.AndroidApplicationConventionPlugin"
        }
        register("kotlinMultiplatform") {
            id = "dev.rsierov.kotlin.multiplatform"
            implementationClass = "dev.rsierov.gradle.KotlinMultiplatformConventionPlugin"
        }
        register("kotlinAndroid") {
            id = "dev.rsierov.kotlin.android"
            implementationClass = "dev.rsierov.gradle.KotlinAndroidConventionPlugin"
        }
        register("androidLibrary") {
            id = "dev.rsierov.android.library"
            implementationClass = "dev.rsierov.gradle.AndroidLibraryConventionPlugin"
        }
        register("compose") {
            id = "dev.rsierov.compose.multiplatform"
            implementationClass = "dev.rsierov.gradle.ComposeMultiplatformConventionPlugin"
        }
    }
}
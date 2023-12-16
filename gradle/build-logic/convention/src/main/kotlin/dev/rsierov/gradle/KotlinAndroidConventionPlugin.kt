package dev.rsierov.gradle

import dev.rsierov.gradle.internal.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KotlinAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
            }
            configureKotlin()
        }
    }
}

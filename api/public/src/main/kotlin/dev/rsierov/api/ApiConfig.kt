package dev.rsierov.api

import java.util.Locale

data class ApiConfig(
    val baseUrl: String,
    val apiKey: String,
    val itemsPerPage: Int = 15,
    val locale: () -> Locale,
)

val ApiConfig.language: String
    get() = if ("nl" == locale().isO3Language) "nl" else "en"
package dev.rsierov.api

import java.util.Locale

data class ApiConfig(
    val baseUrl: String,
    val apiKey: String,
    val itemsPerPage: Int = ITEMS_PER_PAGE_DEFAULT,
    val locale: () -> Locale,
) {
    companion object {
        const val ITEMS_PER_PAGE_DEFAULT = 15
    }
}

val ApiConfig.language: String
    get() = if ("nl" == locale().isO3Language) "nl" else "en"
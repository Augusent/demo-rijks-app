@file:Suppress("FunctionName")

package dev.rsierov.data.fake

import dev.rsierov.api.ApiConfig
import java.util.Locale

fun TestApiConfig(
    baseUrl: String = "http://localhost/api",
    apiKey: String = "test-api-key",
    itemsPerPage: Int = ApiConfig.ITEMS_PER_PAGE_DEFAULT,
    locale: () -> Locale = Locale::getDefault,
): ApiConfig = ApiConfig(
    baseUrl = baseUrl,
    itemsPerPage = itemsPerPage,
    apiKey = apiKey,
    locale = locale,
)
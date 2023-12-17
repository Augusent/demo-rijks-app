package dev.rsierov.api.rijks.wiring

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rsierov.api.rijks.internal.KtorHttpLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class HttpClientModule {

    @Provides
    @Singleton
    fun json(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun httpEngine(): HttpClientEngine {
        val okhttp = OkHttpClient.Builder()
            .build()
        val okHttpConfig = OkHttpConfig().apply {
            preconfigured = okhttp
        }
        return OkHttpEngine(config = okHttpConfig)
    }

    @Provides
    @Singleton
    fun httpClient(
        json: Json,
        engine: HttpClientEngine,
    ): HttpClient = HttpClient(engine) {
        install(ContentNegotiation) { json(json, contentType = ContentType.Application.Json) }
        install(Logging) { logger = KtorHttpLogger; level = LogLevel.INFO }
    }
}
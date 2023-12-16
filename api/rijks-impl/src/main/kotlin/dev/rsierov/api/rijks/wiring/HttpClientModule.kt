package dev.rsierov.api.rijks.wiring

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class HttpClientModule {

    @Provides
    @Singleton
    fun httpClient(): HttpClient = HttpClient {

    }
}
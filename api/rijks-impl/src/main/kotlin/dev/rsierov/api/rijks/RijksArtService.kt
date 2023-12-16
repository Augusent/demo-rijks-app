package dev.rsierov.api.rijks

import dev.rsierov.api.ArtCollectionPage
import dev.rsierov.api.ArtService
import dev.rsierov.api.result.ApiResult
import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RijksArtService @Inject constructor(
    private val httpClient: HttpClient,
) : ArtService {

    override suspend fun getArtCollectionPage(page: Int): ApiResult<ArtCollectionPage, Unit> {
        TODO()
    }
}
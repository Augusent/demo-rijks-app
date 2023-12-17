package dev.rsierov.api.rijks

import dev.rsierov.api.ApiConfig
import dev.rsierov.api.ArtCollectionPage
import dev.rsierov.api.ArtService
import dev.rsierov.api.Sorting
import dev.rsierov.api.language
import dev.rsierov.api.result.ApiResult
import dev.rsierov.api.rijks.internal.RequestExecutor
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RijksArtService @Inject constructor(
    private val apiConfig: ApiConfig,
    private val httpClient: HttpClient,
    private val requestExecutor: RequestExecutor,
) : ArtService {

    override suspend fun getArtCollectionPage(
        page: Int,
        sortBy: Sorting,
        itemsPerPage: Int?,
    ): ApiResult<ArtCollectionPage, Unit> {
        return requestExecutor.execute {
            httpClient.get("${apiConfig.baseUrl}/${apiConfig.language}/collection") {
                parameter("key", apiConfig.apiKey)
                parameter("ps", itemsPerPage ?: apiConfig.itemsPerPage)
                parameter("s", sortBy.stringify())
            }
        }
    }
}
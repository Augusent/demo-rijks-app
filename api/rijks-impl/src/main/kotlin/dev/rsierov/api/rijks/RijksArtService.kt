package dev.rsierov.api.rijks

import dev.rsierov.api.ApiConfig
import dev.rsierov.api.ArtCollectionPage
import dev.rsierov.api.ArtService
import dev.rsierov.api.Sorting
import dev.rsierov.api.language
import dev.rsierov.api.result.ApiResult
import dev.rsierov.api.result.map
import dev.rsierov.api.rijks.internal.RequestExecutor
import dev.rsierov.domain.model.DetailedArtObject
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.Serializable
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
                parameter("p", page)
                parameter("key", apiConfig.apiKey)
                parameter("ps", itemsPerPage ?: apiConfig.itemsPerPage)
                parameter("s", sortBy.stringify())
            }
        }
    }

    override suspend fun getArtDetails(objectNumber: String): ApiResult<DetailedArtObject, Unit> {
        @Serializable
        class Body(val artObject: DetailedArtObject)

        val response = requestExecutor.execute<Body, Unit> {
            httpClient.get("${apiConfig.baseUrl}/${apiConfig.language}/collection/$objectNumber") {
                parameter("key", apiConfig.apiKey)
            }
        }
        return response.map { it.artObject }
    }
}
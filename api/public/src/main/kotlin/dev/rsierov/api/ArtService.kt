package dev.rsierov.api

import dev.rsierov.api.result.ApiResult
import dev.rsierov.domain.model.ArtObject
import dev.rsierov.domain.model.CountFacets
import dev.rsierov.domain.model.FacetsItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface ArtService {
    suspend fun getArtCollectionPage(page: Int): ApiResult<ArtCollectionPage, Unit>
}

@Serializable
data class ArtCollectionPage(
    @SerialName("artObjects") val artObjects: List<ArtObject> = emptyList(),
    @SerialName("countFacets") val countFacets: CountFacets,
    @SerialName("count") val count: Int = 0,
    @SerialName("facets") val facets: List<FacetsItem>? = null,
    @SerialName("elapsedMilliseconds") val elapsedMilliseconds: Int = 0,
)
package dev.rsierov.data.fake

import dev.rsierov.api.ArtCollectionPage
import dev.rsierov.api.ArtService
import dev.rsierov.api.Sorting
import dev.rsierov.api.result.ApiResult
import dev.rsierov.domain.model.ArtObject
import dev.rsierov.domain.model.CountFacets
import dev.rsierov.domain.model.DetailedArtObject

class FakeArtService : ArtService {

    internal var artCollectionPageResponse: ApiResult<ArtCollectionPage, Unit>? = null
    internal var artDetailsResponse: ApiResult<DetailedArtObject, Unit>? = null

    override suspend fun getArtCollectionPage(
        page: Int,
        sortBy: Sorting,
        itemsPerPage: Int?
    ): ApiResult<ArtCollectionPage, Unit> =
        checkNotNull(artCollectionPageResponse) {
            "call respond*ArtCollectionPage() function first"
        }

    override suspend fun getArtDetails(objectNumber: String): ApiResult<DetailedArtObject, Unit> =
        checkNotNull(artDetailsResponse) {
            "call respond*ArtDetails() function first"
        }
}

fun FakeArtService.respondSuccessArtCollectionPage(items: List<ArtObject>) {
    val page = ArtCollectionPage(
        artObjects = items,
        count = items.size,
        countFacets = CountFacets(),
    )
    artCollectionPageResponse = ApiResult.Success(page)
}

fun FakeArtService.respondFailureArtCollectionPage() {
    artCollectionPageResponse = ApiResult.httpFailure(code = 500)
}

fun FakeArtService.respondSuccessArtDetails(item: DetailedArtObject) {
    artDetailsResponse = ApiResult.Success(item)
}

fun FakeArtService.respondFailureArtDetails() {
    artDetailsResponse = ApiResult.httpFailure(code = 500)
}
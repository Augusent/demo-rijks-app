package dev.rsierov.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.rsierov.api.ApiConfig
import dev.rsierov.api.ArtService
import dev.rsierov.api.cause
import dev.rsierov.api.result.ApiResult
import dev.rsierov.data.repository.ArtCollectionRepository.Companion.FIRST_PAGE
import dev.rsierov.data.repository.ArtCollectionRepository.Companion.MAX_ITEMS
import dev.rsierov.domain.model.ArtObject
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ArtCollectionRepository(
    private val apiConfig: ApiConfig,
    private val pagingSourceFactory: () -> PagingSource<Int, ArtObject>,
) {

    @Inject
    internal constructor(
        apiConfig: ApiConfig,
        pagingSourceProvider: Provider<ArtPagingSource>,
    ) : this(
        apiConfig = apiConfig,
        pagingSourceFactory = pagingSourceProvider::get,
    )

    fun getArtCollectionPager(): Pager<Int, ArtObject> = Pager(
        config = PagingConfig(
            enablePlaceholders = false,
            pageSize = apiConfig.itemsPerPage,
            maxSize = MAX_ITEMS, // defined by the api
        ),
        initialKey = null,
        pagingSourceFactory = pagingSourceFactory,
    )

    companion object {
        internal const val FIRST_PAGE = 1 // 0th and 1st are the same in Rijks API
        internal const val MAX_ITEMS = 10_000 // defined by Rijks API
    }
}

internal class ArtPagingSource @Inject constructor(
    private val artService: ArtService,
) : PagingSource<Int, ArtObject>() {

    override fun getRefreshKey(state: PagingState<Int, ArtObject>): Int =
        ((state.anchorPosition ?: FIRST_PAGE) - state.config.initialLoadSize / 2)
            .coerceAtLeast(FIRST_PAGE)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObject> {
        val page = params.key ?: FIRST_PAGE
        return when (val result = artService.getArtCollectionPage(page)) {
            is ApiResult.Failure -> LoadResult.Error(result.cause)
            is ApiResult.Success -> LoadResult.Page(
                data = result.response.artObjects,
                prevKey = (page - 1).takeUnless { it <= FIRST_PAGE },
                nextKey = (page + 1).takeUnless { it * params.loadSize >= MAX_ITEMS },
            )
        }
    }
}
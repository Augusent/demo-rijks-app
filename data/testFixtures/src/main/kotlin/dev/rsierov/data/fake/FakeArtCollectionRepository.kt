@file:Suppress("FunctionName")

package dev.rsierov.data.fake

import androidx.paging.PagingSourceFactory
import androidx.paging.testing.asPagingSourceFactory
import dev.rsierov.api.ApiConfig
import dev.rsierov.data.repository.ArtCollectionRepository
import dev.rsierov.domain.model.ArtObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

fun FakeArtCollectionRepository(
    itemsSource: Flow<List<ArtObject>>,
    coroutineScope: CoroutineScope,
    apiConfig: ApiConfig = TestApiConfig(),
): ArtCollectionRepository = ArtCollectionRepository(
    apiConfig = apiConfig,
    pagingSourceFactory = PagingSourceFactory(itemsSource.asPagingSourceFactory(coroutineScope))
)
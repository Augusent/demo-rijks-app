@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.rsierov.feature.art.gallery

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.rsierov.data.repository.ArtCollectionRepository
import dev.rsierov.domain.model.ArtObject
import dev.rsierov.feature.art.gallery.ArtGalleryItem.ArtistHeader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class ArtGalleryWorkflow @AssistedInject constructor(
    @Assisted private val coroutineScope: CoroutineScope,
    private val artCollectionRepository: ArtCollectionRepository,
) {

    private val actions: MutableSharedFlow<Action> = MutableSharedFlow(replay = 1)

    val gallery = actions.filterIsInstance<Action.InitLoad>()
        .flatMapLatest {
            artCollectionRepository.getArtCollectionPager()
                .flow.map(PagingData<ArtObject>::groupedByArtist)
        }
        .cachedIn(coroutineScope)

    fun loadGallery() {
        actions.tryEmit(Action.InitLoad)
    }

    private sealed class Action {
        data object InitLoad : Action()
    }

    @AssistedFactory
    interface Factory {
        fun create(coroutineScope: CoroutineScope): ArtGalleryWorkflow
    }
}

private fun PagingData<ArtObject>.groupedByArtist(): PagingData<ArtGalleryItem> {
    fun shouldSeparate(before: ArtObject, after: ArtObject) =
        !before.principalOrFirstMaker.equals(after.principalOrFirstMaker, ignoreCase = true)

    return map { ArtGalleryItem.PieceOfArt(it) }
        .insertSeparators { before, after ->
            when {
                before != null && after != null && shouldSeparate(before.art, after.art) ->
                    ArtistHeader(name = after.art.principalOrFirstMaker)

                before == null && after != null ->
                    ArtistHeader(name = after.art.principalOrFirstMaker)

                else -> null // no header needed
            }
        }
}
package dev.rsierov.feature.art.details

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.rsierov.api.ArtService
import dev.rsierov.api.result.ApiResult
import dev.rsierov.domain.model.DetailedArtObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtDetailsWorkflow @AssistedInject constructor(
    @Assisted private val objectNumber: String,
    @Assisted private val coroutineScope: CoroutineScope,
    private val service: ArtService,
) {

    private val _details: MutableStateFlow<ApiResult<DetailedArtObject, Unit>?> = MutableStateFlow(null)
    val details: StateFlow<ApiResult<DetailedArtObject, Unit>?> get() = _details

    fun loadDetails() {
        coroutineScope.launch {
            _details.update {
                service.getArtDetails(objectNumber = objectNumber)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            objectNumber: String,
            coroutineScope: CoroutineScope,
        ): ArtDetailsWorkflow
    }
}

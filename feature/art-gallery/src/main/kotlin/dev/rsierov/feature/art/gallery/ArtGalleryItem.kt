package dev.rsierov.feature.art.gallery

import dev.rsierov.domain.model.ArtObject

sealed class ArtGalleryItem {
    data class ArtistHeader(val name: String): ArtGalleryItem()

    data class PieceOfArt(val art: ArtObject): ArtGalleryItem()
}

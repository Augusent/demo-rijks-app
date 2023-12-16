package dev.rsierov.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtObject(
    @SerialName("principalOrFirstMaker") val principalOrFirstMaker: String = "",
    @SerialName("webImage") val webImage: WebImage? = null,
    @SerialName("headerImage") val headerImage: HeaderImage,
    @SerialName("objectNumber") val objectNumber: String = "",
    @SerialName("links") val links: Links,
    @SerialName("hasImage") val hasImage: Boolean = false,
    @SerialName("showImage") val showImage: Boolean = false,
    @SerialName("id") val id: String = "",
    @SerialName("title") val title: String = "",
    @SerialName("longTitle") val longTitle: String = "",
    @SerialName("permitDownload") val permitDownload: Boolean = false
)
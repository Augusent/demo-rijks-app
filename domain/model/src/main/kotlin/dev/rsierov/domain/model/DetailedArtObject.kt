package dev.rsierov.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailedArtObject(
    @SerialName("objectNumber") val objectNumber: String,
    @SerialName("title") val title: String,
    @SerialName("titles") val titles: List<String>,
    @SerialName("description") val description: String? = null,
    @SerialName("webImage") val webImage: WebImage? = null,
    @SerialName("hasImage") val hasImage: Boolean = false,
)
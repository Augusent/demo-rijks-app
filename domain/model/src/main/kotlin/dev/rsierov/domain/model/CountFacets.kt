package dev.rsierov.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountFacets(
    @SerialName("ondisplay") val onDisplay: Int = 0,
    @SerialName("hasimage") val hasImage: Int = 0
)
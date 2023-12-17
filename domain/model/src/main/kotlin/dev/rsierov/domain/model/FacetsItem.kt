package dev.rsierov.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacetsItem(
    @SerialName("prettyName") val prettyName: Int = 0,
    @SerialName("otherTerms") val otherTerms: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("facets") val facets: List<FacetsItem>? = null,
)
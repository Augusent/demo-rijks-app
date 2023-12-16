package dev.rsierov.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebImage(
    @SerialName("offsetPercentageY") val offsetPercentageY: Int = 0,
    @SerialName("offsetPercentageX") val offsetPercentageX: Int = 0,
    @SerialName("width") val width: Int = 0,
    @SerialName("guid") val guid: String = "",
    @SerialName("url") val url: String = "",
    @SerialName("height") val height: Int = 0
)
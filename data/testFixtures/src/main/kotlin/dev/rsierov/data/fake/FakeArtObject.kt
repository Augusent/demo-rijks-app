@file:Suppress("FunctionName")

package dev.rsierov.data.fake

import dev.rsierov.domain.model.ArtObject
import dev.rsierov.domain.model.HeaderImage
import dev.rsierov.domain.model.Links
import dev.rsierov.domain.model.WebImage

fun FakeArtObject(
    testKey: String,
    title: String = "Title-$testKey",
    author: String = "Author-$testKey",
    longTitle: String = "LongTitle-$testKey",
    imageUrl: String? = "https://lh3.ggpht.com/TqYUx95cGBi6Q8WdYiFIjpbWHayBjfj4WnOv4Aqg6o3ef5CWKXaK3eASUtNLy-Edj0cOnLvkySsVKV4KsoAdkqJ0e-s=s0",
    objectNumber: String = "object#$testKey",
): ArtObject = ArtObject(
    principalOrFirstMaker = author,
    webImage = imageUrl?.let { WebImage(url = it) },
    headerImage = HeaderImage(url = imageUrl ?: ""),
    objectNumber = objectNumber,
    links = Links(),
    hasImage = imageUrl != null,
    showImage = imageUrl != null,
    id = testKey,
    title = title,
    longTitle = longTitle,
)

fun FakeArtObjectList(size: Int, seed: Int = 0) =
    List(size) { FakeArtObject(testKey = "${seed + it}") }
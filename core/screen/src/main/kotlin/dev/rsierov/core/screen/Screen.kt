package dev.rsierov.core.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface Screen {

    @Composable
    fun Content(navController: NavHostController)

    @Suppress("FunctionName")
    companion object Destination {
        const val ArtGallery = "home/art-gallery"
        const val ArtDetails = "home/art-gallery/{art_id}"

        fun ArtDetails(artId: String) = ArtDetails.replace("{art_id}", artId)
    }
}
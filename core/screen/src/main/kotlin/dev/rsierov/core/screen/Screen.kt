package dev.rsierov.core.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface Screen {

    @Composable
    fun Content(navController: NavHostController)
}
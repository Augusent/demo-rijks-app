package dev.rsierov.rijks

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.rsierov.core.screen.Screen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var screens: Map<String, @JvmSuppressWildcards Screen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "home/art-gallery",
            ) {
                screens.forEach { (route, screen) ->
                    composable(route) {
                        with(screen) {
                            Content(navController)
                        }
                    }
                }
            }
        }
    }
}
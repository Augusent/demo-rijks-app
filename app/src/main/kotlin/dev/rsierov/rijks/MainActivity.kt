package dev.rsierov.rijks

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.rsierov.core.screen.Screen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var screens: Map<String, @JvmSuppressWildcards Screen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = screens.getValue("home/art-gallery")
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                with(screen) {
                    Content()
                }
            }
        }
    }
}
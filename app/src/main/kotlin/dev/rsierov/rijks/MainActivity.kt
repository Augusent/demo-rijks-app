package dev.rsierov.rijks

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dev.rsierov.api.ArtCollectionPage
import dev.rsierov.api.ArtService
import dev.rsierov.api.cause
import dev.rsierov.api.result.ApiResult
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var artService: ArtService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                val collection by produceState<ApiResult<ArtCollectionPage, Unit>?>(null) {
                    value = artService.getArtCollectionPage(0)
                }
                when (val result = collection) {
                    is ApiResult.Success -> {
                        LazyColumn {
                            items(result.response.artObjects) { art ->
                                Text(
                                    text = art.title,
                                    modifier = Modifier.height(56.dp)
                                )
                            }
                        }
                    }

                    is ApiResult.Failure -> {
                        SideEffect {
                            Log.e("MainActivity", "Failed to get collection", result.cause)
                        }
                        Text(
                            text = "Failed!!!",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    null -> {
                        Text(
                            text = "Loading...",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
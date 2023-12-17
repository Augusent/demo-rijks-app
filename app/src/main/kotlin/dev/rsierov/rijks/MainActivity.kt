package dev.rsierov.rijks

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import dev.rsierov.data.repository.ArtCollectionRepository
import dev.rsierov.domain.model.ArtObject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var artCollectionRepository: ArtCollectionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                val artPaged = remember { artCollectionRepository.getArtCollectionPager() }
                val collection = artPaged.flow.collectAsLazyPagingItems()
                LazyColumn {
                    if (collection.loadState.refresh == LoadState.Loading) {
                        item {
                            Text(
                                text = "Waiting for items to load from the backend",
                                modifier = Modifier.fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    items(
                        count = collection.itemCount,
                        contentType = { ArtObject::class },
                        key = { collection.peek(it)?.id ?: it }
                    ) { index ->
                        val art = collection[index] ?: return@items
                        Column(modifier = Modifier.height(64.dp)) {
                            Text(
                                text = art.title,
                                fontWeight = FontWeight.Black,
                            )
                            Text(
                                text = art.principalOrFirstMaker,
                                fontStyle = FontStyle.Italic,
                            )
                            Text(text = "Art Object: #${art.objectNumber}")
                        }
                    }

                    if (collection.loadState.append == LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier.fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}
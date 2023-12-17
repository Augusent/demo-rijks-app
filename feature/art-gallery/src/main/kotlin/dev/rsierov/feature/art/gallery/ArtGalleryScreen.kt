@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)

package dev.rsierov.feature.art.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rsierov.core.screen.Screen
import dev.rsierov.domain.model.ArtObject
import dev.rsierov.feature.art.gallery.ui.ArtItem
import dev.rsierov.feature.art.gallery.ui.ArtistHeader
import javax.inject.Inject

internal class ArtGalleryScreen @Inject constructor() : Screen {

    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<ViewModel>()
        val workflow = viewModel.workflow
        val gallery = workflow.gallery.collectAsLazyPagingItems()

        LaunchedEffect(workflow) {
            workflow.loadGallery()
        }
        ArtGalleryContent(
            gallery = gallery,
            onRetryPage = gallery::retry,
            onRefreshGallery = gallery::refresh,
            onArtClick = { navController.navigate("home/art-gallery/{art_id}") },
            modifier = Modifier.fillMaxSize()
        )
    }

    @HiltViewModel
    class ViewModel @Inject constructor(
        workflowFactory: ArtGalleryWorkflow.Factory,
    ) : androidx.lifecycle.ViewModel() {
        val workflow = workflowFactory.create(viewModelScope)
    }
}

@Composable
fun ArtGalleryContent(
    gallery: LazyPagingItems<ArtGalleryItem>,
    modifier: Modifier,
    onRetryPage: () -> Unit,
    onRefreshGallery: () -> Unit,
    onArtClick: (ArtObject) -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = gallery.isRefreshing,
        onRefresh = onRefreshGallery,
    )

    Box(modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .pullRefresh(state = pullRefreshState)
        ) {
            if (gallery.itemCount == 0) {
                preGalleryContent(
                    gallery = gallery,
                    onRefreshGallery = onRefreshGallery,
                )
            } else {
                mainGalleryContent(
                    gallery = gallery,
                    onArtClick = onArtClick,
                )
                appendGalleryContent(
                    gallery = gallery,
                    onRetryPage = onRetryPage,
                )
            }
        }

        PullRefreshIndicator(
            state = pullRefreshState,
            refreshing = gallery.isRefreshing,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

private fun LazyListScope.mainGalleryContent(
    gallery: LazyPagingItems<ArtGalleryItem>,
    onArtClick: (ArtObject) -> Unit,
) {
    for (index in 0 until gallery.itemCount) {
        when (val peekedItem = gallery.peek(index) ?: continue) {
            is ArtGalleryItem.ArtistHeader -> stickyHeader(
                key = index,
                contentType = ArtGalleryItem.ArtistHeader::class,
            ) {
                ArtistHeader(
                    header = gallery[index] as ArtGalleryItem.ArtistHeader,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is ArtGalleryItem.PieceOfArt -> item(
                key = peekedItem.art.id,
                contentType = ArtGalleryItem.PieceOfArt::class,
            ) {
                val item = gallery[index] as ArtGalleryItem.PieceOfArt
                ArtItem(
                    item = item,
                    onClick = onArtClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

private fun LazyListScope.preGalleryContent(
    gallery: LazyPagingItems<ArtGalleryItem>,
    onRefreshGallery: () -> Unit,
) {
    when (gallery.loadState.refresh) {
        is LoadState.Loading -> item {
            Text(
                text = "Waiting for art to load from Rijks",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 32.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

        is LoadState.Error -> item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Failed to load art. Please, retry.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 32.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
                Button(
                    onClick = onRefreshGallery,
                    content = { Text(text = "Reload") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                )
            }
        }

        is LoadState.NotLoading -> {
            // no-op
        }
    }
}

private fun LazyListScope.appendGalleryContent(
    gallery: LazyPagingItems<ArtGalleryItem>,
    onRetryPage: () -> Unit
) {
    when (gallery.loadState.append) {
        LoadState.Loading -> {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }

        is LoadState.Error -> {
            item {
                Button(
                    onClick = onRetryPage,
                    content = { Text(text = "Retry") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                )
            }
        }

        is LoadState.NotLoading -> {
            // no-op
        }
    }
}

private val LazyPagingItems<ArtGalleryItem>.isRefreshing
    get() = loadState.refresh == LoadState.Loading && itemCount > 0
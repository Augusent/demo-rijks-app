package dev.rsierov.feature.art.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rsierov.api.result.ApiResult
import dev.rsierov.core.screen.Screen
import dev.rsierov.domain.model.DetailedArtObject
import javax.inject.Inject

internal class ArtDetailsScreen @Inject constructor() : Screen {

    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<ViewModel>()
        val workflow = viewModel.workflow
        val details by workflow.details.collectAsState()

        LaunchedEffect(workflow) {
            workflow.loadDetails()
        }
        ArtDetailsContent(
            details = details,
            onReload = workflow::loadDetails,
            modifier = Modifier.fillMaxSize()
        )
    }

    @HiltViewModel
    class ViewModel @Inject constructor(
        savedStateHandle: SavedStateHandle,
        workflowFactory: ArtDetailsWorkflow.Factory,
    ) : androidx.lifecycle.ViewModel() {
        val workflow = workflowFactory.create(
            objectNumber = requireNotNull(savedStateHandle["art_id"]) { "art_id must not be null" },
            coroutineScope = viewModelScope,
        )
    }
}

@Composable
fun ArtDetailsContent(
    details: ApiResult<DetailedArtObject, Unit>?,
    onReload: () -> Unit,
    modifier: Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        when (details) {
            is ApiResult.Failure -> Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Failed to get the art. Please, retry.",
                    style = MaterialTheme.typography.titleMedium,
                )
                Button(
                    onClick = onReload,
                    content = { Text(text = "Retry") }
                )
            }

            is ApiResult.Success -> {
                ArtObjectContent(
                    artObject = details.response,
                    modifier = Modifier.fillMaxSize()
                )
            }

            null -> {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun ArtObjectContent(
    artObject: DetailedArtObject,
    modifier: Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        artObject.webImage?.let { image ->
            AsyncImage(
                model = image.url,
                contentDescription = "hero art image",
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = artObject.title,
                style = MaterialTheme.typography.titleLarge,
            )
            (artObject.titles - artObject.title).forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            artObject.description?.let { desc ->
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

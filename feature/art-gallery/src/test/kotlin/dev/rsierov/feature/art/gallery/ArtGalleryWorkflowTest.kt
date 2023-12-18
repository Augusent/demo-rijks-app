package dev.rsierov.feature.art.gallery

import androidx.paging.testing.asSnapshot
import dev.rsierov.data.fake.FakeArtCollectionRepository
import dev.rsierov.data.fake.FakeArtObjectList
import dev.rsierov.domain.model.ArtObject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ArtGalleryWorkflowTest {

    @Test
    fun `art gallery comes separated by author in sections`() = runTest {
        val source = MutableSharedFlow<List<ArtObject>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )
        val repository = FakeArtCollectionRepository(
            itemsSource = source,
            coroutineScope = backgroundScope,
        )
        val workflow = ArtGalleryWorkflow(
            coroutineScope = backgroundScope,
            artCollectionRepository = repository
        )

        workflow.loadGallery()
        source.emit(FakeArtObjectList(15)) // it gives us 15 unique authors

        val gallery = workflow.gallery.asSnapshot()
        assertTrue(gallery.size == 30, "15 unique authors leads to 15 + 15 items in total")

        val expectedHeaders = gallery.filterIndexed { i, _ -> i % 2 == 0 } // every odd is a header
        val actualHeaders = gallery.filterIsInstance<ArtGalleryItem.ArtistHeader>()
        assertEquals(expectedHeaders, actualHeaders, "every odd item is a header")

        assertEquals(actualHeaders, actualHeaders.distinct(), "every header is unique")
    }
}
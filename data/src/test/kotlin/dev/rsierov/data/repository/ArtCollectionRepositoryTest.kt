package dev.rsierov.data.repository

import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import androidx.paging.testing.asSnapshot
import dev.rsierov.data.fake.FakeArtObjectList
import dev.rsierov.data.fake.FakeArtService
import dev.rsierov.data.fake.TestApiConfig
import dev.rsierov.data.fake.respondFailureArtCollectionPage
import dev.rsierov.data.fake.respondSuccessArtCollectionPage
import dev.rsierov.data.repository.ArtCollectionRepository.ArtPagingSource
import dev.rsierov.data.repository.ArtCollectionRepository.Companion.toDefaultPagingConfig
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

class ArtCollectionRepositoryTest {

    private val fakeArtService: FakeArtService = FakeArtService()
    private val artPagingSource: ArtPagingSource = ArtPagingSource(fakeArtService)

    @Test
    fun `art collection pager appends items on scroll`() = runTest {
        val repository = ArtCollectionRepository(
            apiConfig = TestApiConfig(),
            pagingSourceFactory = { artPagingSource },
        )
        val pager = repository.getArtCollectionPager()

        fakeArtService.respondSuccessArtCollectionPage(FakeArtObjectList(size = 15, seed = 0))
        val initial = pager.flow.asSnapshot()
        assertTrue(initial.size == 15, "initial gallery contains 15 items")

        fakeArtService.respondSuccessArtCollectionPage(FakeArtObjectList(size = 15, seed = 1))
        val appendedOnce = pager.flow.asSnapshot { scrollTo(20) }
        assertTrue(appendedOnce.size == 30, "appended once gallery contains 30 items")

        fakeArtService.respondSuccessArtCollectionPage(FakeArtObjectList(size = 15, seed = 2))
        val appendedTwice = pager.flow.asSnapshot { scrollTo(25) }
        assertTrue(appendedTwice.size == 45, "appended twice gallery contains 45 items")
    }

    @Test
    fun `art collection paging source appends gallery after retry`() = runTest {
        val pager = TestPager(
            config = TestApiConfig().toDefaultPagingConfig(),
            pagingSource = artPagingSource,
        )

        fakeArtService.respondSuccessArtCollectionPage(FakeArtObjectList(size = 15, seed = 0))
        val initial = pager.refresh() as PagingSource.LoadResult.Page
        assertTrue(initial.data.size == 15, "initial gallery size is 15")

        fakeArtService.respondFailureArtCollectionPage()
        assertTrue(pager.append() is PagingSource.LoadResult.Error)
        assertTrue(pager.getPages().size == 1, "gallery is still single page on error")

        fakeArtService.respondSuccessArtCollectionPage(FakeArtObjectList(size = 15, seed = 1))
        assertTrue(pager.append() is PagingSource.LoadResult.Page, "gallery is appended on retry")
        assertTrue(pager.getPages().size == 2, "gallery total page number is 2 now")
    }

    @Test
    fun `art collection paging source is empty on failed initial load`() = runTest {
        val apiConfig = TestApiConfig()
        val pager = TestPager(
            config = apiConfig.toDefaultPagingConfig(),
            pagingSource = artPagingSource,
        )

        fakeArtService.respondFailureArtCollectionPage()
        assertTrue(pager.refresh() is PagingSource.LoadResult.Error, "gallery failed to load")
        assertTrue(pager.getPages().isEmpty(), "gallery remains empty")
    }
}

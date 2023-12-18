@file:OptIn(ExperimentalTestApi::class)

package dev.rsierov.rijks

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollToKeyAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAncestors
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToKey
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.rsierov.core.screen.Screen
import dev.rsierov.data.fake.TestApiConfig
import dev.rsierov.rijks.utils.assets
import dev.rsierov.rijks.utils.enqueueResponseFrom
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Rule
import org.junit.Test
import java.util.Locale

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AppIntegrationInstrumentedTest {

    private val server = MockWebServer()

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    val apiConfig = TestApiConfig(
        baseUrl = server.url("/api").toString(),
        locale = { Locale.US },
    )

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun monkeyInteractionWithPaginationAndNavigationToArtDetails() {
        with(assets) {
            server.enqueueResponseFrom(
                open("response-collection-page-1.json"),
                open("response-collection-page-2.json"),
                open("response-collection-details-frigate.json")
            )
        }

        composeRule.waitUntilAtLeastOneExists(modelOfArtificialReefArt)

        composeRule.onNode(modelOfArtificialReefArt)
            .assertIsDisplayed()

        composeRule.onNode(hasScrollToKeyAction())
            .performScrollToKey("NG-MC-663")

        composeRule.onNode(frigateArtItem)
            .performClick()

        composeRule.waitUntilAtLeastOneExists(artDetailsScreen)

        composeRule.onNode(artDetailsScreen)
            .assertIsDisplayed()

        composeRule.waitUntilAtLeastOneExists(frigateDetailsTitle)

        composeRule.onNode(frigateDetailsTitle)
            .assertExists()
            .assertIsDisplayed()
    }
}

private val modelOfArtificialReefArt
    get() = hasText("#NG-MC-807")

private val frigateArtItem
    get() = hasText("#NG-MC-663")

private val artDetailsScreen
    get() = hasTestTag(Screen.ArtDetails)

private val frigateDetailsTitle
    get() = hasText("Model of a 32-Gun Frigate")
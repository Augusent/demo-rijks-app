package dev.rsierov.rijks.utils

import android.content.res.AssetManager
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import java.io.InputStream

val assets: AssetManager
    get() = InstrumentationRegistry.getInstrumentation().context.resources.assets

fun MockWebServer.enqueueResponseFrom(
    vararg sources: InputStream
) {
    sources.forEach {
        val response = MockResponse()
            .setHeader("Content-Type", "application/json")
            .setBody(Buffer().readFrom(it))
        enqueue(response)
    }
}
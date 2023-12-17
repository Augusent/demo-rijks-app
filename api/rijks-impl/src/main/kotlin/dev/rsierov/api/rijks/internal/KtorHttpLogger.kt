package dev.rsierov.api.rijks.internal

import android.util.Log
import io.ktor.client.plugins.logging.Logger

internal object KtorHttpLogger : Logger {
    override fun log(message: String) {
        Log.v("HTTP", message)
    }
}
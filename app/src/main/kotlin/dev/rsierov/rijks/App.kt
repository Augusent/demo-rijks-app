package dev.rsierov.rijks

import android.app.Application
import androidx.core.os.ConfigurationCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import dev.rsierov.api.ApiConfig
import java.util.Locale
import javax.inject.Provider
import javax.inject.Singleton

@HiltAndroidApp
class App : Application()

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun appLocale(application: Application): Locale =
        ConfigurationCompat.getLocales(application.resources.configuration)[0]
            ?: Locale.getDefault()

    @Provides
    @Singleton
    fun apiConfig(localeProvider: Provider<Locale>): ApiConfig = ApiConfig(
        baseUrl = "https://www.rijksmuseum.nl/api",
        apiKey = "0fiuZFh4",
        locale = localeProvider::get,
    )
}
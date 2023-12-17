package dev.rsierov.feature.art.details.wiring

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import dev.rsierov.core.screen.Screen
import dev.rsierov.feature.art.details.ArtDetailsScreen

@InstallIn(ActivityComponent::class)
@Module
internal interface ArtDetailsBinderModule {

    @Binds
    @IntoMap
    @StringKey(Screen.Destination.ArtDetails)
    fun artDetails(screen: ArtDetailsScreen): Screen
}
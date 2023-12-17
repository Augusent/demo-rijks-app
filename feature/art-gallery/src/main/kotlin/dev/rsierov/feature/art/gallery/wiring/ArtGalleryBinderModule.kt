package dev.rsierov.feature.art.gallery.wiring

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import dev.rsierov.core.screen.Screen
import dev.rsierov.feature.art.gallery.ArtGalleryScreen

@InstallIn(ActivityComponent::class)
@Module
internal interface ArtGalleryBinderModule {

    @Binds
    @IntoMap
    @StringKey(Screen.Destination.ArtGallery)
    fun artGallery(screen: ArtGalleryScreen): Screen
}

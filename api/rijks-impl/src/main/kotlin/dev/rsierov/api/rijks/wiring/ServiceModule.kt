package dev.rsierov.api.rijks.wiring

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rsierov.api.ArtService
import dev.rsierov.api.rijks.RijksArtService

@InstallIn(SingletonComponent::class)
@Module
internal interface ServiceBinderModule {

    @Binds
    fun artService(service: RijksArtService): ArtService
}
package com.hyunwoo.cliendroid

import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.hyunwoo.cliendroid.data.DataLayerModule
import com.hyunwoo.cliendroid.domain.DomainLayerModule
import com.hyunwoo.cliendroid.presentation.PresentationModule
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [
        PresentationModule::class,
        DomainLayerModule::class,
        DataLayerModule::class
    ]
)
class AppModule {

    @Named("ApplicationScope")
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope =
        CoroutineScope(Dispatchers.Main + CoroutineName("ApplicationContext") + SupervisorJob())

    @Provides
    @Singleton
    fun provideCoilGifLoader(application: ClienApplication): ImageLoader =
        ImageLoader.Builder(application.applicationContext).apply {
            componentRegistry {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder())
                } else {
                    add(GifDecoder())
                }
            }
        }.build()
}

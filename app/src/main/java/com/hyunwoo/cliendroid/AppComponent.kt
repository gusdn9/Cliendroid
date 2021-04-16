package com.hyunwoo.cliendroid

import com.hyunwoo.cliendroid.network.HostType
import com.hyunwoo.cliendroid.network.NetworkProvider
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        NetworkProvider::class
    ],
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        AssistedInjectModule::class
    ]
)
interface AppComponent : AndroidInjector<ClienApplication> {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: ClienApplication): Builder

        fun networkProvider(networkProvider: NetworkProvider): Builder
    }

    companion object {

        fun create(application: ClienApplication): AppComponent {

            val networkProvider = NetworkProvider.create(
                application,
                getHostType(),
                BuildConfig.DEBUG
            )

            return DaggerAppComponent.builder()
                .application(application)
                .networkProvider(networkProvider)
                .build()
        }

        private fun getHostType(): HostType =
            HostType.PROD
    }
}

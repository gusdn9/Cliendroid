package com.hyunwoo.cliendroid

import com.hyunwoo.cliendroid.network.CookieStore
import com.hyunwoo.cliendroid.network.CookieStoreProvider
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
interface AppComponent : AndroidInjector<ClienApplication>, CookieStoreProvider {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: ClienApplication): Builder

        fun networkProvider(networkProvider: NetworkProvider): Builder
    }

    companion object {

        fun create(application: ClienApplication): AppComponent {
            val bridge = DelegationBridge()

            val networkProvider = NetworkProvider.create(
                getHostType(),
                bridge,
                BuildConfig.DEBUG
            )

            val component = DaggerAppComponent.builder()
                .application(application)
                .networkProvider(networkProvider)
                .build()
            bridge.component = component

            return component
        }

        private fun getHostType(): HostType =
            HostType.PROD
    }

    private class DelegationBridge : CookieStoreProvider {
        lateinit var component: AppComponent

        override fun provideCookieStore(): CookieStore =
            component.provideCookieStore()
    }
}

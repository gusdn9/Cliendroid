package com.hyunwoo.cliendroid.network

import com.hyunwoo.cliendroid.network.service.AuthInfraService
import com.hyunwoo.cliendroid.network.service.CommunityInfraService
import com.hyunwoo.cliendroid.network.service.UserInfraService
import javax.inject.Named

interface NetworkProvider {

    @Named("Prod")
    fun provideProdHostType(): HostType

    @Named("Mobile")
    fun provideMobileHostType(): HostType

    fun provideCommunityService(): CommunityInfraService

    fun provideAuthService(): AuthInfraService

    fun provideUserService(): UserInfraService

    companion object {
        fun create(
            hostType: HostType,
            cookieStoreProvider: CookieStoreProvider,
            debug: Boolean
        ): NetworkProvider =
            DaggerNetworkComponent.builder()
                .hostType(hostType)
                .cookieStoreProvider(cookieStoreProvider)
                .debug(debug)
                .build()
    }
}

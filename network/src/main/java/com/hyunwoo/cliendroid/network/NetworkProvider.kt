package com.hyunwoo.cliendroid.network

import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named

interface NetworkProvider {

    @Named("Prod")
    fun provideProdHostType(): HostType

    @Named("Mobile")
    fun provideMobileHostType(): HostType

    @Named("Debug")
    fun provideDebug(): Boolean

    fun provideOkHttpClient(): OkHttpClient

    fun provideRetrofit(): Retrofit

    @Named("EveryoneParkForum")
    fun provideEveryOneParkForumRetrofit(): Retrofit

    @Named("Auth")
    fun provideAuthRetrofit(): Retrofit

    fun provideMoshi(): Moshi

    fun provideCommunityService(): CommunityInfraService

    fun provideAuthService(): AuthInfraService

    @Named("EveryoneParkForum")
    fun provideEveryoneParkForumConverter(): Converter.Factory

    @Named("EveryoneParkForumDetail")
    fun provideEveryOneParkDetailForumConverter(): Converter.Factory

    @Named("LoginPreparedStatement")
    fun provideLoginPreparedStatementConverter(): Converter.Factory

    companion object {
        fun create(
            context: Context,
            hostType: HostType,
            debug: Boolean
        ): NetworkProvider =
            DaggerNetworkComponent.builder()
                .hostType(hostType)
                .context(context)
                .debug(debug)
                .build()
    }
}

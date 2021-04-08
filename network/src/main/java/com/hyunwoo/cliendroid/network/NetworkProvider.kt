package com.hyunwoo.cliendroid.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named

interface NetworkProvider {

    @Named("Debug")
    fun provideDebug(): Boolean

    fun provideOkHttpClient(): OkHttpClient

    fun provideRetrofit(): Retrofit

    @Named("EveryoneParkForum")
    fun provideEveryOneParkForumRetrofit(): Retrofit

    fun provideMoshi(): Moshi

    fun provideHostType(): HostType

    fun provideCommunityService(): CommunityInfraService

    @Named("EveryoneParkForum")
    fun provideEveryoneParkForumConverter(): Converter.Factory

    companion object {
        fun create(
            hostType: HostType,
            debug: Boolean
        ): NetworkProvider =
            DaggerNetworkComponent.builder()
                .hostType(hostType)
                .debug(debug)
                .build()
    }
}

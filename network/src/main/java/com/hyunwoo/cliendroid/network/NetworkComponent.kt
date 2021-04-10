package com.hyunwoo.cliendroid.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.hyunwoo.cliendroid.network.converter.EveryoneParkForumListConverter
import com.squareup.moshi.Moshi
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class]
)
internal interface NetworkComponent : NetworkProvider {

    @Component.Builder
    interface Builder {
        fun build(): NetworkComponent

        @BindsInstance
        fun hostType(hostType: HostType): Builder

        @BindsInstance
        fun debug(@Named("Debug") debug: Boolean): Builder
    }
}

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        hostType: HostType,
        @Named("Debug") debug: Boolean
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            if (debug) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(loggingInterceptor)

                addNetworkInterceptor(StethoInterceptor())
            }

            addInterceptor { chain ->
                val request = chain.request().newBuilder()
                request.addHeader("Referer", hostType.url)
                request.addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Mobile Safari/537.36"
                )
                chain.proceed(request.build())
            }
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        hostType: HostType,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Named("EveryoneParkForum")
    @Provides
    @Singleton
    fun provideEveryOneParkForumRetrofit(
        hostType: HostType,
        okHttpClient: OkHttpClient,
        @Named("EveryoneParkForum") converter: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(converter)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideCommunityService(@Named("EveryoneParkForum") retrofit: Retrofit): CommunityInfraService =
        retrofit.create(CommunityInfraService::class.java)

    @Named("EveryoneParkForum")
    @Provides
    @Singleton
    fun provideEveryOneParkForumConverter(): Converter.Factory =
        EveryoneParkForumListConverter.create()

    companion object {

        private const val CONNECT_TIMEOUT: Long = 30_000
        private const val READ_TIMEOUT: Long = 30_000
        private const val WRITE_TIMEOUT: Long = 30_000
    }
}

package com.hyunwoo.cliendroid.network

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.hyunwoo.cliendroid.network.converter.auth.LoginConverter
import com.hyunwoo.cliendroid.network.converter.auth.PreparedStatementConverter
import com.hyunwoo.cliendroid.network.converter.everyonepark.EveryoneParkForumDetailConverter
import com.hyunwoo.cliendroid.network.converter.everyonepark.EveryoneParkForumListConverter
import com.hyunwoo.cliendroid.network.interceptor.AddCookiesInterceptor
import com.hyunwoo.cliendroid.network.interceptor.ReceivedCookiesInterceptor
import com.squareup.moshi.Moshi
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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
        fun context(context: Context): Builder

        @BindsInstance
        fun hostType(hostType: HostType): Builder

        @BindsInstance
        fun debug(@Named("Debug") debug: Boolean): Builder
    }
}

@Module
internal class NetworkModule {

    @Named("Prod")
    @Provides
    @Singleton
    fun provideProdHostType(): HostType =
        HostType.PROD

    @Named("Mobile")
    @Provides
    @Singleton
    fun provideMobileHostType(): HostType =
        HostType.MOBILE

    @Provides
    @Singleton
    fun provideAddCookiesInterceptor(context: Context): AddCookiesInterceptor =
        AddCookiesInterceptor(context)

    @Provides
    @Singleton
    fun provideReceivedCookiesInterceptor(context: Context): ReceivedCookiesInterceptor =
        ReceivedCookiesInterceptor(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("Prod") hostType: HostType,
        @Named("Debug") debug: Boolean,
        addCookiesInterceptor: AddCookiesInterceptor,
        receivedCookiesInterceptor: ReceivedCookiesInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            interceptors().add(addCookiesInterceptor)
            interceptors().add(receivedCookiesInterceptor)
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
        @Named("Prod") hostType: HostType,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Named("EveryoneParkForum")
    @Provides
    @Singleton
    fun provideEveryOneParkForumRetrofit(
        @Named("Prod") hostType: HostType,
        okHttpClient: OkHttpClient,
        @Named("EveryoneParkForum") listConverter: Converter.Factory,
        @Named("EveryoneParkForumDetail") detailConverter: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .addConverterFactory(listConverter)
            .addConverterFactory(detailConverter)
            .client(okHttpClient)
            .build()

    @Named("Auth")
    @Provides
    @Singleton
    fun provideAuthRetrofit(
        @Named("Mobile") hostType: HostType,
        okHttpClient: OkHttpClient,
        @Named("LoginPreparedStatement") loginPreparedStatementConverter: Converter.Factory,
        @Named("Login") loginConverter: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .addConverterFactory(loginPreparedStatementConverter)
            .addConverterFactory(loginConverter)
            .addConverterFactory(ScalarsConverterFactory.create())
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

    @Provides
    @Singleton
    fun provideAuthService(@Named("Auth") retrofit: Retrofit): AuthInfraService =
        retrofit.create(AuthInfraService::class.java)

    @Named("EveryoneParkForum")
    @Provides
    @Singleton
    fun provideEveryOneParkForumConverter(): Converter.Factory =
        EveryoneParkForumListConverter.create()

    @Named("EveryoneParkForumDetail")
    @Provides
    @Singleton
    fun provideEveryOneParkDetailForumConverter(): Converter.Factory =
        EveryoneParkForumDetailConverter.create()

    @Named("LoginPreparedStatement")
    @Provides
    @Singleton
    fun provideLoginPreparedStatementConverter(): Converter.Factory =
        PreparedStatementConverter.create()

    @Named("Login")
    @Provides
    @Singleton
    fun provideLoginConverter(): Converter.Factory =
        LoginConverter.create()

    companion object {

        private const val CONNECT_TIMEOUT: Long = 30_000
        private const val READ_TIMEOUT: Long = 30_000
        private const val WRITE_TIMEOUT: Long = 30_000
    }
}

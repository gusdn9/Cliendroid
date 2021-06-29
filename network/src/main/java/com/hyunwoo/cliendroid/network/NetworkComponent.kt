package com.hyunwoo.cliendroid.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.hyunwoo.cliendroid.network.converter.auth.LoginConverter
import com.hyunwoo.cliendroid.network.converter.auth.PreparedStatementConverter
import com.hyunwoo.cliendroid.network.converter.forum.BoardListConverter
import com.hyunwoo.cliendroid.network.converter.forum.ForumDetailConverter
import com.hyunwoo.cliendroid.network.converter.forum.ForumListConverter
import com.hyunwoo.cliendroid.network.converter.forum.SearchListConverter
import com.hyunwoo.cliendroid.network.converter.search.SearchAuthConverter
import com.hyunwoo.cliendroid.network.converter.user.UserCommentConverter
import com.hyunwoo.cliendroid.network.converter.user.UserInfoConverter
import com.hyunwoo.cliendroid.network.converter.user.UserPostConverter
import com.hyunwoo.cliendroid.network.interceptor.AddCookiesInterceptor
import com.hyunwoo.cliendroid.network.interceptor.ReceivedCookiesInterceptor
import com.hyunwoo.cliendroid.network.service.AuthInfraService
import com.hyunwoo.cliendroid.network.service.CommunityAuthInfraService
import com.hyunwoo.cliendroid.network.service.CommunityInfraService
import com.hyunwoo.cliendroid.network.service.SearchInfraService
import com.hyunwoo.cliendroid.network.service.UserInfraService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [CookieStoreProvider::class],
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

        fun cookieStoreProvider(cookieStoreProvider: CookieStoreProvider): Builder
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
    fun provideAddCookiesInterceptor(cookieStore: CookieStore): AddCookiesInterceptor =
        AddCookiesInterceptor(cookieStore)

    @Provides
    @Singleton
    fun provideReceivedCookiesInterceptor(cookieStore: CookieStore): ReceivedCookiesInterceptor =
        ReceivedCookiesInterceptor(cookieStore)

    @Named("WithoutAuth")
    @Provides
    @Singleton
    fun provideWithoutAuthOkHttpClient(
        @Named("Prod") hostType: HostType,
        @Named("Debug") debug: Boolean
    ): OkHttpClient = buildOkHttp(hostType, debug)

    @Named("Auth")
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(
        @Named("Prod") hostType: HostType,
        @Named("Debug") debug: Boolean,
        addCookiesInterceptor: AddCookiesInterceptor,
    ): OkHttpClient = buildOkHttp(hostType, debug, addCookiesInterceptor)

    @Named("Login")
    @Provides
    @Singleton
    fun provideLoginOkHttpClient(
        @Named("Prod") hostType: HostType,
        @Named("Debug") debug: Boolean,
        addCookiesInterceptor: AddCookiesInterceptor,
        receivedCookiesInterceptor: ReceivedCookiesInterceptor
    ): OkHttpClient = buildOkHttp(hostType, debug, addCookiesInterceptor, receivedCookiesInterceptor)

    private fun buildOkHttp(
        hostType: HostType,
        debug: Boolean,
        addCookiesInterceptor: AddCookiesInterceptor? = null,
        receivedCookiesInterceptor: ReceivedCookiesInterceptor? = null
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            if (addCookiesInterceptor != null) interceptors().add(addCookiesInterceptor)
            if (receivedCookiesInterceptor != null) interceptors().add(receivedCookiesInterceptor)
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
        @Named("WithoutAuth") okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Named("Forum")
    @Provides
    @Singleton
    fun provideForumRetrofit(
        @Named("Prod") hostType: HostType,
        @Named("WithoutAuth") okHttpClient: OkHttpClient,
        @Named("Forum") listConverter: Converter.Factory,
        @Named("ForumDetail") detailConverter: Converter.Factory,
        @Named("SearchList") searchListConverter: Converter.Factory,
        @Named("BoardList") boardListConverter: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .addConverterFactory(listConverter)
            .addConverterFactory(detailConverter)
            .addConverterFactory(searchListConverter)
            .addConverterFactory(boardListConverter)
            .client(okHttpClient)
            .build()

    @Named("ForumAuth")
    @Provides
    @Singleton
    fun provideForumAuthRetrofit(
        @Named("Mobile") hostType: HostType,
        @Named("Auth") okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Named("Auth")
    @Provides
    @Singleton
    fun provideAuthRetrofit(
        @Named("Mobile") hostType: HostType,
        @Named("Login") okHttpClient: OkHttpClient,
        @Named("LoginPreparedStatement") loginPreparedStatementConverter: Converter.Factory,
        @Named("Login") loginConverter: Converter.Factory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .addConverterFactory(loginPreparedStatementConverter)
            .addConverterFactory(loginConverter)
            .client(okHttpClient)
            .build()

    @Named("User")
    @Provides
    @Singleton
    fun provideUserRetrofit(
        @Named("Mobile") hostType: HostType,
        @Named("Auth") okHttpClient: OkHttpClient,
        @Named("UserPost") userPostConverter: Converter.Factory,
        @Named("UserComment") userCommentConverter: Converter.Factory,
        @Named("UserInfo") userInfoConverter: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .addConverterFactory(userPostConverter)
            .addConverterFactory(userCommentConverter)
            .addConverterFactory(userInfoConverter)
            .client(okHttpClient)
            .build()

    @Named("SearchAuth")
    @Provides
    @Singleton
    fun provideSearchAuthRetrofit(
        @Named("Mobile") hostType: HostType,
        @Named("Auth") okHttpClient: OkHttpClient,
        @Named("SearchAuth") searchAuthConverter: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostType.url)
            .addConverterFactory(searchAuthConverter)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    /**
     * Provide Services
     */

    @Provides
    @Singleton
    fun provideCommunityService(@Named("Forum") retrofit: Retrofit): CommunityInfraService =
        retrofit.create(CommunityInfraService::class.java)

    @Provides
    @Singleton
    fun provideCommunityAuthService(@Named("ForumAuth") retrofit: Retrofit): CommunityAuthInfraService =
        retrofit.create(CommunityAuthInfraService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(@Named("Auth") retrofit: Retrofit): AuthInfraService =
        retrofit.create(AuthInfraService::class.java)

    @Provides
    @Singleton
    fun provideUserService(@Named("User") retrofit: Retrofit): UserInfraService =
        retrofit.create(UserInfraService::class.java)

    @Provides
    @Singleton
    fun provideSearchAuthService(@Named("SearchAuth") retrofit: Retrofit): SearchInfraService =
        retrofit.create(SearchInfraService::class.java)

    /**
     * Provide Converter
     */

    @Named("Forum")
    @Provides
    @Singleton
    fun provideForumConverter(): Converter.Factory =
        ForumListConverter.create()

    @Named("ForumDetail")
    @Provides
    @Singleton
    fun provideForumDetailForumConverter(): Converter.Factory =
        ForumDetailConverter.create()

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

    @Named("UserInfo")
    @Provides
    @Singleton
    fun provideUserInfoConverter(): Converter.Factory =
        UserInfoConverter.create()

    @Named("SearchList")
    @Provides
    @Singleton
    fun provideSearchListConverter(): Converter.Factory =
        SearchListConverter.create()

    @Named("BoardList")
    @Provides
    @Singleton
    fun provideBoardListConverter(): Converter.Factory =
        BoardListConverter.create()

    @Named("UserPost")
    @Provides
    @Singleton
    fun provideUserPostConverter(): Converter.Factory =
        UserPostConverter.create()

    @Named("UserComment")
    @Provides
    @Singleton
    fun provideUserCoomentConverter(): Converter.Factory =
        UserCommentConverter.create()

    @Named("SearchAuth")
    @Provides
    @Singleton
    fun provideSearchAuthConverter(): Converter.Factory =
        SearchAuthConverter.create()

    companion object {

        private const val CONNECT_TIMEOUT: Long = 30_000
        private const val READ_TIMEOUT: Long = 30_000
        private const val WRITE_TIMEOUT: Long = 30_000
    }
}

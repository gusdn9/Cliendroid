package net.meshkorea.android.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
        @Named("Debug") debug: Boolean
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (debug) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)

            builder.addNetworkInterceptor(StethoInterceptor())
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
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder().build()

}

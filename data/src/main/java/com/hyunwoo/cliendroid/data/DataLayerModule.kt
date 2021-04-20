package com.hyunwoo.cliendroid.data

import com.hyunwoo.cliendroid.data.repository.AuthRepositoryImpl
import com.hyunwoo.cliendroid.data.repository.CommunityRepositoryImpl
import com.hyunwoo.cliendroid.data.repository.CookieDataSourceImpl
import com.hyunwoo.cliendroid.data.repository.CookieRepositoryImpl
import com.hyunwoo.cliendroid.data.repository.CookieStoreImpl
import com.hyunwoo.cliendroid.data.repository.LoggedInUserDataSourceImpl
import com.hyunwoo.cliendroid.domain.repository.AuthRepository
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import com.hyunwoo.cliendroid.domain.repository.CookieDataSource
import com.hyunwoo.cliendroid.domain.repository.CookieRepository
import com.hyunwoo.cliendroid.domain.repository.LoggedInUserDataSource
import com.hyunwoo.cliendroid.network.CookieStore
import dagger.Binds
import dagger.Module

@Module(includes = [AssistedInjectModule::class, DataLayerBindingModule::class])
class DataLayerModule

@Module
interface DataLayerBindingModule {

    /**
     * Repository 관련
     */

    @Binds
    fun bindCommunityRepository(impl: CommunityRepositoryImpl): CommunityRepository

    @Binds
    fun bindCookieStore(impl: CookieStoreImpl): CookieStore

    @Binds
    fun bindLoggedInUserDataSource(impl: LoggedInUserDataSourceImpl): LoggedInUserDataSource

    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindCookieDataSource(impl: CookieDataSourceImpl): CookieDataSource

    @Binds
    fun bindCookieRepository(impl: CookieRepositoryImpl): CookieRepository
}

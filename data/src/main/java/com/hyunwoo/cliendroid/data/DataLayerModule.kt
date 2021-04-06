package com.hyunwoo.cliendroid.data

import com.hyunwoo.cliendroid.data.repository.CommunityRepositoryImpl
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
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
}

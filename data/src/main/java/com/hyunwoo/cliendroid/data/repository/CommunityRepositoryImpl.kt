package com.hyunwoo.cliendroid.data.repository

import com.hyunwoo.cliendroid.data.mapper.toEveryOneParkForum
import com.hyunwoo.cliendroid.domain.model.EveryOneParkForum
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import com.hyunwoo.cliendroid.network.CommunityInfraService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepositoryImpl @Inject constructor(
    private val communityService: CommunityInfraService
) : CommunityRepository {

    override suspend fun getEveryOneParkForum(page: Int): List<EveryOneParkForum> =
        communityService.getEveryOneParkForum(page)
            .map { forum -> forum.toEveryOneParkForum() }
}

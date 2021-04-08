package com.hyunwoo.cliendroid.data.repository

import com.hyunwoo.cliendroid.data.mapper.toEveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import com.hyunwoo.cliendroid.network.CommunityInfraService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepositoryImpl @Inject constructor(
    private val communityService: CommunityInfraService
) : CommunityRepository {

    override suspend fun getEveryoneParkForum(page: Int): List<EveryoneParkForum> =
        communityService.getEveryoneParkForum(page)
            .map { forum -> forum.toEveryoneParkForum() }
}

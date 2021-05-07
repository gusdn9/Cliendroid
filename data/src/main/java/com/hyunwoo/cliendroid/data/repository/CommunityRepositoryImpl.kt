package com.hyunwoo.cliendroid.data.repository

import com.hyunwoo.cliendroid.data.mapper.toBlockedEveryoneParkForum
import com.hyunwoo.cliendroid.data.mapper.toEveryoneParkForum
import com.hyunwoo.cliendroid.data.mapper.toEveryoneParkForumContent
import com.hyunwoo.cliendroid.data.mapper.toMenuBoards
import com.hyunwoo.cliendroid.data.mapper.toSearchContent
import com.hyunwoo.cliendroid.domain.model.BaseEveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForumContent
import com.hyunwoo.cliendroid.domain.model.MenuBoards
import com.hyunwoo.cliendroid.domain.model.SearchContent
import com.hyunwoo.cliendroid.domain.model.SearchSort
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import com.hyunwoo.cliendroid.network.service.CommunityInfraService
import com.hyunwoo.cliendroid.network.model.everyonepark.BlockedEveryoneParkForumItemDto
import com.hyunwoo.cliendroid.network.model.everyonepark.EveryoneParkForumItemDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepositoryImpl @Inject constructor(
    private val communityService: CommunityInfraService
) : CommunityRepository {

    override suspend fun getEveryoneParkForumList(page: Int): List<BaseEveryoneParkForum> =
        communityService.getEveryoneParkForumList(page)
            .contents.map { forum ->
                when (forum) {
                    is EveryoneParkForumItemDto -> forum.toEveryoneParkForum()
                    is BlockedEveryoneParkForumItemDto -> forum.toBlockedEveryoneParkForum()
                }
            }

    override suspend fun getEveryoneParkForumDetail(detailUrl: String): EveryoneParkForumContent =
        communityService.getEveryoneParkForumDetail(detailUrl)
            .toEveryoneParkForumContent()

    override suspend fun search(keyword: String, page: Int, sort: SearchSort?, boardId: String?): SearchContent =
        communityService.search(keyword, page, sort?.value, boardId)
            .toSearchContent()

    override suspend fun getMenuList(): MenuBoards =
        communityService.getBoardList().toMenuBoards()
}

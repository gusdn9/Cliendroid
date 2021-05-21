package com.hyunwoo.cliendroid.data.repository

import com.hyunwoo.cliendroid.data.mapper.toBlockedForum
import com.hyunwoo.cliendroid.data.mapper.toForum
import com.hyunwoo.cliendroid.data.mapper.toForumContent
import com.hyunwoo.cliendroid.data.mapper.toMenuBoards
import com.hyunwoo.cliendroid.data.mapper.toSearchContent
import com.hyunwoo.cliendroid.domain.model.forum.BaseForum
import com.hyunwoo.cliendroid.domain.model.forum.ForumContent
import com.hyunwoo.cliendroid.domain.model.MenuBoards
import com.hyunwoo.cliendroid.domain.model.search.board.SearchContent
import com.hyunwoo.cliendroid.domain.model.search.board.SearchSort
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import com.hyunwoo.cliendroid.network.model.forum.BlockedForumItemDto
import com.hyunwoo.cliendroid.network.model.forum.ForumItemDto
import com.hyunwoo.cliendroid.network.service.CommunityInfraService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepositoryImpl @Inject constructor(
    private val communityService: CommunityInfraService
) : CommunityRepository {

    override suspend fun getForumList(link: String, page: Int): List<BaseForum> =
        communityService.getForumList(link, page)
            .contents.map { forum ->
                when (forum) {
                    is ForumItemDto -> forum.toForum()
                    is BlockedForumItemDto -> forum.toBlockedForum()
                }
            }

    override suspend fun getForumDetail(detailUrl: String): ForumContent =
        communityService.getForumDetail(detailUrl)
            .toForumContent()

    override suspend fun search(keyword: String, page: Int, sort: SearchSort?, boardId: String?): SearchContent =
        communityService.search(keyword, page, sort?.value, boardId)
            .toSearchContent()

    override suspend fun getMenuList(): MenuBoards =
        communityService.getMenuList().toMenuBoards()
}

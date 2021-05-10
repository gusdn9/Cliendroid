package com.hyunwoo.cliendroid.domain.repository

import com.hyunwoo.cliendroid.domain.model.BaseForum
import com.hyunwoo.cliendroid.domain.model.ForumContent
import com.hyunwoo.cliendroid.domain.model.MenuBoards
import com.hyunwoo.cliendroid.domain.model.SearchContent
import com.hyunwoo.cliendroid.domain.model.SearchSort

interface CommunityRepository {

    suspend fun getForumList(link: String, page: Int): List<BaseForum>

    suspend fun getForumDetail(detailUrl: String): ForumContent

    suspend fun search(
        keyword: String,
        page: Int,
        sort: SearchSort? = null,
        boardId: String? = null
    ): SearchContent

    suspend fun getMenuList(): MenuBoards
}

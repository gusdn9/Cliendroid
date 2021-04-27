package com.hyunwoo.cliendroid.domain.repository

import com.hyunwoo.cliendroid.domain.model.BaseEveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForumContent
import com.hyunwoo.cliendroid.domain.model.SearchContent
import com.hyunwoo.cliendroid.domain.model.SearchSort

interface CommunityRepository {

    suspend fun getEveryoneParkForumList(page: Int): List<BaseEveryoneParkForum>

    suspend fun getEveryoneParkForumDetail(detailUrl: String): EveryoneParkForumContent

    suspend fun search(
        keyword: String,
        page: Int,
        sort: SearchSort? = null,
        boardId: String? = null
    ): SearchContent
}

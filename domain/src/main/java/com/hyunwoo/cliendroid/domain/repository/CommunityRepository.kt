package com.hyunwoo.cliendroid.domain.repository

import com.hyunwoo.cliendroid.domain.model.BaseEveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForumContent

interface CommunityRepository {

    suspend fun getEveryoneParkForumList(page: Int): List<BaseEveryoneParkForum>

    suspend fun getEveryoneParkForumDetail(detailUrl: String): EveryoneParkForumContent
}

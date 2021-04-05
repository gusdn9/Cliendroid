package com.hyunwoo.cliendroid.domain.repository

import com.hyunwoo.cliendroid.domain.model.EveryOneParkForum

interface CommunityRepository {

    suspend fun getEveryOneParkForum(page: Int): List<EveryOneParkForum>
}

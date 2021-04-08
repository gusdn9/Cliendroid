package com.hyunwoo.cliendroid.domain.repository

import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum

interface CommunityRepository {

    suspend fun getEveryoneParkForum(page: Int): List<EveryoneParkForum>
}

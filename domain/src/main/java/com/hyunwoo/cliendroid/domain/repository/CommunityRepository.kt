package com.hyunwoo.cliendroid.domain.repository

interface CommunityRepository {

    suspend fun getEveryOneParkForum(page: Int)
}

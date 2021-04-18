package com.hyunwoo.cliendroid.domain.repository

import com.hyunwoo.cliendroid.domain.model.UserInfo

interface AuthRepository {

    suspend fun loginPreparedStatement(): String

    suspend fun login(id: String, password: String, csrf: String): Boolean

    suspend fun getUserInfo(id: String): UserInfo
}

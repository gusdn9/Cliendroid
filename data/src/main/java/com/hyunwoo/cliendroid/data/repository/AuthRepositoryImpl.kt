package com.hyunwoo.cliendroid.data.repository

import com.hyunwoo.cliendroid.data.mapper.toUserInfo
import com.hyunwoo.cliendroid.domain.model.UserInfo
import com.hyunwoo.cliendroid.domain.repository.AuthRepository
import com.hyunwoo.cliendroid.network.AuthInfraService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthInfraService
) : AuthRepository {

    override suspend fun loginPreparedStatement(): String =
        authService.loginPreparedStatement().csrf

    override suspend fun login(id: String, password: String, csrf: String): Boolean =
        authService.login(id, password, csrf).result

    override suspend fun getUserInfo(id: String): UserInfo =
        authService.getUserInfo(id).toUserInfo()
}

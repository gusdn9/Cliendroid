package com.hyunwoo.cliendroid.data.repository

import com.hyunwoo.cliendroid.data.mapper.toUserInfo
import com.hyunwoo.cliendroid.domain.model.UserInfo
import com.hyunwoo.cliendroid.domain.repository.AuthRepository
import com.hyunwoo.cliendroid.network.service.AuthInfraService
import com.hyunwoo.cliendroid.network.service.UserInfraService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthInfraService,
    private val userService: UserInfraService
) : AuthRepository {

    override suspend fun loginPreparedStatement(): String =
        authService.loginPreparedStatement().csrf

    override suspend fun login(id: String, password: String, csrf: String): Boolean =
        authService.login(id, password, csrf).result

    override suspend fun getUserInfo(id: String): UserInfo =
        userService.getUserInfo(id).toUserInfo()
}

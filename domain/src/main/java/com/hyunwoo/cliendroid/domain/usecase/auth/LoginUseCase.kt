package com.hyunwoo.cliendroid.domain.usecase.auth

import com.hyunwoo.cliendroid.domain.exception.LoginFailedException
import com.hyunwoo.cliendroid.domain.model.LoggedInUser
import com.hyunwoo.cliendroid.domain.repository.AuthRepository
import com.hyunwoo.cliendroid.domain.repository.CookieRepository
import com.hyunwoo.cliendroid.domain.repository.LoggedInUserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loggedInUserRepository: LoggedInUserRepository,
    private val cookieRepository: CookieRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(id: String, password: String): LoggedInUser {
        cookieRepository.setCookie(null)
        val csrf = authRepository.loginPreparedStatement()
        try {
            val loginResult = authRepository.login(id, password, csrf)
            if (loginResult.not()) throw LoginFailedException()
        } catch (e: Exception) {
            throw LoginFailedException()
        }
        val userInfo = authRepository.getUserInfo(id)
        val loggedInUser = LoggedInUser(
            userId = id,
            userNickname = userInfo.nickname,
            userEmail = userInfo.email,
            startDate = userInfo.startDate
        )
        loggedInUserRepository.login(loggedInUser)
        return loggedInUser
    }
}

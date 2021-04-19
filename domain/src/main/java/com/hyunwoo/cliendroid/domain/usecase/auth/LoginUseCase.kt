package com.hyunwoo.cliendroid.domain.usecase.auth

import com.hyunwoo.cliendroid.domain.exception.LoginFailedException
import com.hyunwoo.cliendroid.domain.model.LoggedInUser
import com.hyunwoo.cliendroid.domain.repository.AuthRepository
import com.hyunwoo.cliendroid.domain.repository.LoggedInUserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loggedInUserRepository: LoggedInUserRepository,
    private val authRepository: AuthRepository
) {

    suspend fun invoke(id: String, password: String): LoggedInUser {
        val csrf = authRepository.loginPreparedStatement()
        val loginResult = authRepository.login(id, password, csrf)
        // 위의 작업을 통해서 쿠키가 자동으로 생성되어 넣어졌을텐데 없다면 로그인 실패로 간주
        val cookies = loggedInUserRepository.loggedInUser?.cookies ?: throw LoginFailedException()
        if (loginResult.not()) throw LoginFailedException()
        val userInfo = authRepository.getUserInfo(id)
        val loggedInUser = LoggedInUser(
            cookies = cookies,
            userId = id,
            userNickname = userInfo.nickname,
            userEmail = userInfo.email,
            startDate = userInfo.startDate
        )

        // val loggedInUser = coroutineScope {
        //     val loginJob = async { authRepository.login(id, password, csrf) }
        //     val userInfoJob = async { authRepository.getUserInfo(id) }
        //
        //     val loginResult = loginJob.await()
        //     // 위의 작업을 통해서 쿠키가 자동으로 생성되어 넣어졌을텐데 없다면 로그인 실패로 간주
        //     val cookies = loggedInUserRepository.loggedInUser?.cookies ?: throw LoginFailedException()
        //     if (loginResult.not()) throw LoginFailedException()
        //     val userInfo = userInfoJob.await()
        //
        //     LoggedInUser(
        //         cookies = cookies,
        //         userId = id,
        //         userNickname = userInfo.nickname,
        //         userEmail = userInfo.email,
        //         startDate = userInfo.startDate
        //     )
        // }
        loggedInUserRepository.login(loggedInUser)
        return loggedInUser
    }
}

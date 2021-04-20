package com.hyunwoo.cliendroid.domain.usecase.auth

import com.hyunwoo.cliendroid.domain.model.LoggedInUser
import com.hyunwoo.cliendroid.domain.model.LogoutCause
import com.hyunwoo.cliendroid.domain.repository.CookieRepository
import com.hyunwoo.cliendroid.domain.repository.LoggedInUserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val loggedInUserRepository: LoggedInUserRepository,
    private val cookieRepository: CookieRepository
) {

    fun invoke(cause: LogoutCause): LoggedInUser? {
        cookieRepository.setCookie(null)
        return loggedInUserRepository.logout(cause)
    }
}

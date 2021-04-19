package com.hyunwoo.cliendroid.domain.usecase.auth

import com.hyunwoo.cliendroid.domain.model.LogoutCause
import com.hyunwoo.cliendroid.domain.repository.LoggedInUserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val loggedInUserRepository: LoggedInUserRepository
) {

    suspend fun invoke(cause: LogoutCause) {
        loggedInUserRepository.logout(cause)
    }
}

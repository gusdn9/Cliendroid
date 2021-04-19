package com.hyunwoo.cliendroid.domain.usecase.auth

import com.hyunwoo.cliendroid.domain.model.LoggedInUser
import com.hyunwoo.cliendroid.domain.repository.LoggedInUserRepository
import javax.inject.Inject

class RequireLoggedInUserUseCase @Inject constructor(
    private val loggedInUserRepository: LoggedInUserRepository
) {

    operator fun invoke(): LoggedInUser =
        requireNotNull(loggedInUserRepository.loggedInUser)
}

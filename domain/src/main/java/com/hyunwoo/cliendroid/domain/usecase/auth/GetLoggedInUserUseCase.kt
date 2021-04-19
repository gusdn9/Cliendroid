package com.hyunwoo.cliendroid.domain.usecase.auth

import com.hyunwoo.cliendroid.domain.model.LoggedInUser
import com.hyunwoo.cliendroid.domain.repository.LoggedInUserRepository
import javax.inject.Inject

class GetLoggedInUserUseCase @Inject constructor(
    private val loggedInUserRepository: LoggedInUserRepository
) {

    suspend operator fun invoke(): LoggedInUser? =
        loggedInUserRepository.loggedInUser
}

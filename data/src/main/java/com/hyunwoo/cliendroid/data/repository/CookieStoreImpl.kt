package com.hyunwoo.cliendroid.data.repository

import com.hyunwoo.cliendroid.domain.exception.CookieNotFoundException
import com.hyunwoo.cliendroid.domain.repository.LoggedInUserRepository
import com.hyunwoo.cliendroid.network.CookieStore
import javax.inject.Inject

class CookieStoreImpl @Inject constructor(
    private val loggedInUserRepository: LoggedInUserRepository
) : CookieStore {
    override fun getCookies(): Set<String> {
        val loggedInUser = loggedInUserRepository.loggedInUser ?: throw CookieNotFoundException()
        return loggedInUser.cookies
    }

    override fun saveCookie(cookies: Set<String>) {
        loggedInUserRepository.update { copy(cookies = cookies) }
    }
}

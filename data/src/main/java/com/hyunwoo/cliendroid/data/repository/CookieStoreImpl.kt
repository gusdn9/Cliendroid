package com.hyunwoo.cliendroid.data.repository

import com.hyunwoo.cliendroid.domain.repository.CookieRepository
import com.hyunwoo.cliendroid.network.CookieStore
import javax.inject.Inject

class CookieStoreImpl @Inject constructor(
    private val cookieRepository: CookieRepository
) : CookieStore {
    override fun getCookies(): Set<String>? =
        cookieRepository.getCookie()

    override fun saveCookie(cookies: Set<String>) =
        cookieRepository.setCookie(cookies)
}

package com.hyunwoo.cliendroid.data.repository

import com.hyunwoo.cliendroid.domain.repository.CookieDataSource
import com.hyunwoo.cliendroid.domain.repository.CookieRepository
import javax.inject.Inject

class CookieRepositoryImpl @Inject constructor(
    private val cookieDataSource: CookieDataSource
) : CookieRepository {
    
    override fun getCookie(): Set<String>? =
        cookieDataSource.cookies

    override fun setCookie(cookies: Set<String>?) {
        cookieDataSource.cookies = cookies
    }
}

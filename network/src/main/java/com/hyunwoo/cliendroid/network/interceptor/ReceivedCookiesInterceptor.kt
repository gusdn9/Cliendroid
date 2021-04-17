package com.hyunwoo.cliendroid.network.interceptor

import com.hyunwoo.cliendroid.network.CookieStore
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor(private val cookieStore: CookieStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val setCookie = response.headers("Set-Cookie")
        if (setCookie.isEmpty().not()) {
            val cookies = HashSet<String>()
            setCookie.forEach { cookie ->
                cookies.add(cookie)
            }
            cookieStore.saveCookie(cookies)
        }
        return response
    }
}

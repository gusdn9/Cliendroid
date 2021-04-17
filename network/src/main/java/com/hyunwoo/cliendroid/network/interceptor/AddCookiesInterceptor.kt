package com.hyunwoo.cliendroid.network.interceptor

import com.hyunwoo.cliendroid.network.CookieStore
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor(private val cookieStore: CookieStore) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val getCookies = cookieStore.getCookies()
        getCookies.forEach { cookie ->
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }
}

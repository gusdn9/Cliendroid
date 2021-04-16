package com.hyunwoo.cliendroid.network.interceptor

import android.content.Context
import com.hyunwoo.cliendroid.network.R
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val setCookie = response.headers("Set-Cookie")
        if (setCookie.isEmpty().not()) {
            val cookies = HashSet<String>()
            setCookie.forEach { cookie ->
                cookies.add(cookie)
            }
            val sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.cliendroid_network), Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putStringSet(COOKIES, cookies).apply()
        }
        return response
    }

    companion object {
        private const val COOKIES = "cookies"
    }
}

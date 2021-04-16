package com.hyunwoo.cliendroid.network.interceptor

import android.content.Context
import com.hyunwoo.cliendroid.network.R
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.cliendroid_network), Context.MODE_PRIVATE)

        val getCookies = sharedPreferences.getStringSet(COOKIES, HashSet())

        getCookies?.forEach { cookie ->
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }

    companion object {
        private const val COOKIES = "cookies"
    }
}

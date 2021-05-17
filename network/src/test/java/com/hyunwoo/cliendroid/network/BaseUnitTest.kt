package com.hyunwoo.cliendroid.network

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Before

open class BaseUnitTest {
    protected lateinit var context: Context
    private lateinit var cookieStoreProvider: CookieStoreProvider
    protected lateinit var network: NetworkProvider

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        val sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.cliendroid_network), Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()

        cookieStoreProvider = object : CookieStoreProvider {
            override fun provideCookieStore(): CookieStore {
                return cookieStore
            }

            val cookieStore = object : CookieStore {
                override fun getCookies(): Set<String> {
                    return sharedPreferences.getStringSet("COOKIES", HashSet()) as Set<String>
                }

                override fun saveCookie(cookies: Set<String>) {
                    edit.putStringSet("COOKIES", cookies).apply()
                }
            }
        }

        network = NetworkProvider.create(HostType.PROD, cookieStoreProvider, true)
    }
}

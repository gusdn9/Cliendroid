package com.hyunwoo.cliendroid.network

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ExampleUnitTest {
    private lateinit var context: Context
    private lateinit var cookieStoreProvider: CookieStoreProvider

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
    }

    // @Test
    // fun requestList() = runBlocking {
    //     val network = NetworkProvider.create(HostType.PROD, true)
    //     val forums = network.provideCommunityService().getEveryoneParkForumList(0)
    //     print(forums.contents.size)
    // }

    // @Test
    // fun requestDetail() = runBlocking {
    //     val network = NetworkProvider.create(HostType.PROD, true)
    //     val url = "/service/board/park/16061901"
    //     val res = network.provideCommunityService().getEveryoneParkForumDetail(url)
    //     println(res.htmlBody)
    //     res.comments.forEach { comment ->
    //         when(comment) {
    //             is CommentDto -> println(comment.contents)
    //             is BlockedCommentDto -> println(comment.contents)
    //         }
    //     }
    // }

    @Test
    fun search() = runBlocking {
        val network = NetworkProvider.create(HostType.PROD, cookieStoreProvider, true)
        val service = network.provideCommunityService()
        val result = service.search("검색어")
        print(result.contents.size)
    }

    // @Test
    // fun login() = runBlocking {
    //     val sharedPreferences =
    //         context.getSharedPreferences(context.getString(R.string.cliendroid_network), Context.MODE_PRIVATE)
    //     val edit = sharedPreferences.edit()
    //
    //     val cookieStoreProvider = object : CookieStoreProvider {
    //         override fun provideCookieStore(): CookieStore {
    //             return cookieStore
    //         }
    //
    //         val cookieStore = object : CookieStore {
    //             override fun getCookies(): Set<String> {
    //                 return sharedPreferences.getStringSet("COOKIES", HashSet()) as Set<String>
    //             }
    //
    //             override fun saveCookie(cookies: Set<String>) {
    //                 edit.putStringSet("COOKIES", cookies).apply()
    //             }
    //         }
    //     }
    //
    //     val network = NetworkProvider.create(HostType.MOBILE, cookieStoreProvider, true)
    //     val service = network.provideAuthService()
    //     val loginPreparedStatementRes = service.loginPreparedStatement()
    //     println(loginPreparedStatementRes.csrf)
    //
    //     println("### GET CSRF  ${loginPreparedStatementRes.csrf}")
    //
    //     val id = "test"
    //     val pw = "test"
    //
    //     val result = service.login(id, pw, loginPreparedStatementRes.csrf)
    //
    //     println("### NEXT STEP $result")
    //     val getCookies = sharedPreferences.getStringSet("COOKIES", HashSet())
    //     getCookies?.forEach { cookie ->
    //         println(cookie)
    //         if (cookie.contains("Expires")) {
    //             val expires = cookie.split(";").find { it.contains("Expires") }?.substringAfterLast("=")
    //             println("Expires $expires")
    //             val time = LocalDateTime.parse(expires, DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy HH:mm:ss VV"))
    //         }
    //     }
    //
    //     val info = service.getUserInfo(id)
    //     println("id:$id, nickName:${info.nickname}, email:${info.email}, startDate:${info.startDate}")
    //
    //     //
    //     // val myPage = service.myPage()
    //     // println(myPage.body())
    //
    // }
}

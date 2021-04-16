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

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
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
    fun loginForm() = runBlocking {
        val sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.cliendroid_network), Context.MODE_PRIVATE)

        val network = NetworkProvider.create(context, HostType.MOBILE, true)
        val service = network.provideAuthService()
        val loginPreparedStatementRes = service.loginPreparedStatement()
        println(loginPreparedStatementRes.csrf)

        println("### GET CSRF  ${loginPreparedStatementRes.csrf}")

        val result = service.login("test", "test", loginPreparedStatementRes.csrf)

        println("### NEXT STEP $result")

        val getCookies = sharedPreferences.getStringSet("cookies", HashSet())
        getCookies?.forEach { cookie ->
            println("coockie : $cookie")
        }

        //
        val myPage = service.myPage()
        println(myPage.body())

    }
}

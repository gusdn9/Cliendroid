package com.hyunwoo.cliendroid.network

import android.content.Context
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class AuthUserServiceUnitTest : BaseUnitTest() {
    // FIXME 아아디 패스워드 입력 후 테스트
    private val id = ""
    private val pw = ""

    @Test
    fun login() = runBlocking {
        val authService = network.provideAuthService()
        val userService = network.provideUserService()
        val loginPreparedStatementRes = authService.loginPreparedStatement()
        println(loginPreparedStatementRes.csrf)

        println("### GET CSRF  ${loginPreparedStatementRes.csrf}")

        val result = authService.login(id, pw, loginPreparedStatementRes.csrf)

        val sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.cliendroid_network), Context.MODE_PRIVATE)

        println("### NEXT STEP $result")
        val getCookies = sharedPreferences.getStringSet("COOKIES", HashSet())
        getCookies?.forEach { cookie ->
            println(cookie)
            if (cookie.contains("Expires")) {
                val expires = cookie.split(";").find { it.contains("Expires") }?.substringAfterLast("=")
                println("Expires $expires")
                val time = LocalDateTime.parse(expires, DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy HH:mm:ss VV"))
            }
        }

        val info = userService.getUserInfo(id)
        println("id:$id, nickName:${info.nickname}, email:${info.email}, startDate:${info.startDate}")
    }

    @Test
    fun getUserPostAndComments() = runBlocking {
        login()

        val userService = network.provideUserService()
        try {
            val commentsRes = userService.getUserComments("hikingbo")
            val postRes = userService.getUserPosts("hikingbo")
            println("${postRes.posts.size}, ${commentsRes.userComments.size}")
        } catch (e: Exception) {
            println(e)
        }
    }
}

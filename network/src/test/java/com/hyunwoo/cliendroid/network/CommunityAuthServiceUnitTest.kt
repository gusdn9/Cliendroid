package com.hyunwoo.cliendroid.network

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class CommunityAuthServiceUnitTest : BaseUnitTest() {
    // FIXME 아아디 패스워드 입력 후 테스트
    private val id = ""
    private val pw = ""

    private fun login() = runBlocking {
        val authService = network.provideAuthService()
        val userService = network.provideUserService()
        val loginPreparedStatementRes = authService.loginPreparedStatement()
        println(loginPreparedStatementRes.csrf)

        println("### GET CSRF  ${loginPreparedStatementRes.csrf}")

        val result = authService.login(id, pw, loginPreparedStatementRes.csrf)

        println("### LOGIN RESULT - $result")

        val info = userService.getUserInfo(id)
        println("id:$id, nickName:${info.nickname}, email:${info.email}, startDate:${info.startDate}")
        val info2 = userService.getUserInfo(id)
        println("id:$id, nickName:${info2.nickname}, email:${info2.email}, startDate:${info2.startDate}")
    }

    @Test
    fun writeComment() = runBlocking {
        login()

        val service = network.provideCommunityAuthService()
        // service.commentWrite()
    }

}

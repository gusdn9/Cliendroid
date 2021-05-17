package com.hyunwoo.cliendroid.network

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class SearchAuthUnitTest : BaseUnitTest() {
    // FIXME 아아디 패스워드 입력 후 테스트
    private val id = ""
    private val pw = ""

    fun login() = runBlocking {
        val authService = network.provideAuthService()
        val userService = network.provideUserService()
        val loginPreparedStatementRes = authService.loginPreparedStatement()
        println(loginPreparedStatementRes.csrf)

        println("### GET CSRF  ${loginPreparedStatementRes.csrf}")

        val result = authService.login(id, pw, loginPreparedStatementRes.csrf)

        println("### LOGIN RESULT - $result")

        val info = userService.getUserInfo(id)
        println("id:$id, nickName:${info.nickname}, email:${info.email}, startDate:${info.startDate}")
    }

    @Test
    fun search() = runBlocking {
        login()

        val searchService = network.provideSearchAuthService()
        val searchResult = searchService.search(target = "content", keyword = "재택", page = 0)

    }
}

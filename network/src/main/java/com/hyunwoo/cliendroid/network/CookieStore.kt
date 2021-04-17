package com.hyunwoo.cliendroid.network

import java.time.LocalDateTime

interface CookieStore {

    fun getCookies(): Set<String>

    fun saveCookie(cookies: Set<String>)
}

data class Cookie(
    val cookies: Set<String>,
    val expires: LocalDateTime
)

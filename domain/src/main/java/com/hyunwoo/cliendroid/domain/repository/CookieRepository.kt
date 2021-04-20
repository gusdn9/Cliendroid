package com.hyunwoo.cliendroid.domain.repository

interface CookieRepository {

    fun getCookie(): Set<String>?

    fun setCookie(cookies: Set<String>?)
}

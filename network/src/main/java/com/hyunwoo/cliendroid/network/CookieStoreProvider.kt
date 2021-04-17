package com.hyunwoo.cliendroid.network

interface CookieStoreProvider {
    fun provideCookieStore(): CookieStore
}

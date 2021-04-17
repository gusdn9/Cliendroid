package com.hyunwoo.cliendroid.domain.exception

/**
 * CookieStore에서 getCookie를 하였을때 실패하는 경우에 throw
 */
class CookieNotFoundException : Exception("Cookie not found")

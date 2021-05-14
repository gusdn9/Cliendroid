package com.hyunwoo.cliendroid.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.hyunwoo.cliendroid.domain.repository.CookieDataSource
import javax.inject.Inject

class CookieDataSourceImpl @Inject constructor(
    context: Context
) : CookieDataSource {
    private val pref: SharedPreferences by lazy {
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    }

    override var cookies: Set<String>?
        get() = pref.getStringSet(KEY_COOKIES, null)
        set(value) = pref.edit {
            clear()
            putStringSet(KEY_COOKIES, value)
            apply()
        }

    companion object {
        private const val PREF = "cookie_pref"
        private const val KEY_COOKIES = "cookies"
    }
}

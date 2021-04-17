package com.hyunwoo.cliendroid.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.hyunwoo.cliendroid.data.entity.LoggedInUserEntity
import com.hyunwoo.cliendroid.data.mapper.toLoggedInUser
import com.hyunwoo.cliendroid.data.mapper.toUserEntity
import com.hyunwoo.cliendroid.domain.model.LoggedInUser
import com.hyunwoo.cliendroid.domain.repository.LoggedInUserDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoggedInUserDataSourceImpl @Inject constructor(
    context: Context
) : LoggedInUserDataSource {

    private val pref: SharedPreferences by lazy {
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    }

    override var loggedInUser: LoggedInUser?
        get() = try {
            pref.getString(KEY_LOGGED_IN_USER, null)
                ?.let { MOSHI_LOGGED_IN_USER_ADAPTER.fromJson(it)?.toLoggedInUser() }
        } catch (e: Exception) {
            null
        }
        set(value) = pref.edit {
            putString(KEY_LOGGED_IN_USER, MOSHI_LOGGED_IN_USER_ADAPTER.toJson(value?.toUserEntity()))
        }

    companion object {
        private const val PREF = "user_pref"
        private const val KEY_LOGGED_IN_USER = "logged_in_user"

        private val MOSHI_LOGGED_IN_USER_ADAPTER = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(LoggedInUserEntity::class.java)
    }
}

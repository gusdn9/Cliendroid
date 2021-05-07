package com.hyunwoo.cliendroid.network.converter.auth

import com.hyunwoo.cliendroid.network.model.auth.LoginPreparedStatementRes
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class PreparedStatementConverter : Converter<ResponseBody, LoginPreparedStatementRes> {

    override fun convert(value: ResponseBody): LoginPreparedStatementRes? {
        val document = Jsoup.parse(value.string())
        val loginForm = document.getElementById("loginForm")
        val csrf = loginForm.select("input[name=_csrf]").attr("value")
        return LoginPreparedStatementRes(csrf)
    }

    companion object {
        fun create(): Converter.Factory {
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    if (type != LoginPreparedStatementRes::class.java) return null
                    return PreparedStatementConverter()
                }
            }
        }
    }
}

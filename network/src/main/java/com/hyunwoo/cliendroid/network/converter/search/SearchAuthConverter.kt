package com.hyunwoo.cliendroid.network.converter.search

import com.hyunwoo.cliendroid.network.exception.RequiredLoginException
import com.hyunwoo.cliendroid.network.model.search.auth.BaseSearchAuthItemDto
import com.hyunwoo.cliendroid.network.model.search.auth.SearchAuthRes
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SearchAuthConverter : Converter<ResponseBody, SearchAuthRes> {

    override fun convert(value: ResponseBody): SearchAuthRes? {
        val list: ArrayList<BaseSearchAuthItemDto> = ArrayList()
        val document = Jsoup.parse(value.string())

        val notLogin = document.getElementsByClass("not_login").size > 0
                || document.getElementById("loginForm") != null
        if (notLogin) throw RequiredLoginException()

        return null
    }

    companion object {
        fun create(): Converter.Factory {
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    if (type != SearchAuthRes::class.java) return null
                    return SearchAuthConverter()
                }
            }
        }
    }
}

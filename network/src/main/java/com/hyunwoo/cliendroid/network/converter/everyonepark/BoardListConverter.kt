package com.hyunwoo.cliendroid.network.converter.everyonepark

import com.hyunwoo.cliendroid.network.model.BoardRes
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class BoardListConverter : Converter<ResponseBody, BoardRes> {
    override fun convert(value: ResponseBody): BoardRes? {
        val document = Jsoup.parse(value.string())
        val communities = document.select(".navigation .snb_navmenu .menu-list").forEach {

        }


        val somoim = document.select(".navigation .snb_groupmenu .menu-list")
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
                    if (type != BoardRes::class.java) return null
                    return BoardListConverter()
                }
            }
        }
    }
}

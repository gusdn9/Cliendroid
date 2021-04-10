package com.hyunwoo.cliendroid.network.converter

import com.hyunwoo.cliendroid.network.model.EveryoneParkForumDetailRes
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class EveryoneParkForumDetailConverter : Converter<ResponseBody, EveryoneParkForumDetailRes> {
    override fun convert(value: ResponseBody): EveryoneParkForumDetailRes? {

        val document = Jsoup.parse(value.string())
        val body = document.getElementsByClass("post_article").first().html()
        document.getElementsByClass("comment_row").forEach { reply ->
            val nickname = reply.selectFirst("span.nickname")?.text()
            val nickImg = reply.selectFirst("span.nickimg img")?.attr("src")
            val content = reply.selectFirst(".comment_content input").attr("value")
            val time = reply.selectFirst("span.timestamp").text()
            // 대댓글
            val isRe = reply.select(".comment_row.re")

        }


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
                    if (type != EveryoneParkForumDetailRes::class.java) return null
                    return EveryoneParkForumDetailConverter()
                }
            }
        }
    }
}

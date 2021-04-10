package com.hyunwoo.cliendroid.network.converter

import com.hyunwoo.cliendroid.network.extension.textOrNull
import com.hyunwoo.cliendroid.network.model.EveryoneParkForumListDto
import com.hyunwoo.cliendroid.network.model.UserDto
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class EveryoneParkForumListConverter : Converter<ResponseBody, List<EveryoneParkForumListDto>> {

    override fun convert(value: ResponseBody): List<EveryoneParkForumListDto>? {
        val result: ArrayList<EveryoneParkForumListDto> = ArrayList()
        val document = Jsoup.parse(value.string())
        document.getElementsByClass("list_item").forEach { element ->
            val nickNameClass = element.getElementsByClass("nickname")
            val user = UserDto(
                null,
                nickNameClass.textOrNull(),
                element.selectFirst("img")?.attr("src")
            )
            val isNotice = element.hasClass("notice")

            val subject = element.getElementsByClass("list_subject")
            val title = if (isNotice) {
                element.getElementsByClass("list_subject").text()
            } else {
                element.getElementsByAttribute("title").attr("title")
            }
            val link = subject.attr("href")
            val replyCount = element.getElementsByClass("list_reply").select("span").first()?.text()?.toInt()
            val hit = element.getElementsByClass("list_hit").textOrNull()?.toInt()
            val time = element.getElementsByClass("list_time").text()
            val likes = element.selectFirst(".list_number .list_symph")?.text()?.toInt()
            result.add(
                EveryoneParkForumListDto(
                    title,
                    link,
                    replyCount,
                    hit,
                    time,
                    likes,
                    user
                )
            )
        }

        return result
    }

    companion object {
        fun create(): Converter.Factory {
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *> {
                    return EveryoneParkForumListConverter()
                }
            }
        }
    }
}

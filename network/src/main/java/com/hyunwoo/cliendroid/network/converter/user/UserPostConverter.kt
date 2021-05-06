package com.hyunwoo.cliendroid.network.converter.user

import com.hyunwoo.cliendroid.network.model.UserPostItemDto
import com.hyunwoo.cliendroid.network.model.UserPostRes
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class UserPostConverter : Converter<ResponseBody, UserPostRes> {
    override fun convert(value: ResponseBody): UserPostRes? {
        val document = Jsoup.parse(value.string())

        // val likes: String,
        // val title: String,
        // val boardName: String,
        // val replyCount: String,
        // val time: String,
        // val link: String

        document.getElementsByClass("list_item").forEach { item ->
            val likes = item.select(".list_symph").text()
            val subject = item.select(".list_subject span")
            val boardName = subject[0].text()
            val title = subject[1].text()
            val replyCount = item.select(".list_reply").text()
            val time = item.selectFirst(".time").ownText()
            val link = item.select(".list_subject").attr("href")

            UserPostItemDto(
                likes = likes,
                title = title,
                boardName = boardName,
                replyCount = replyCount,
                time = time,
                link = link
            )
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
                    if (type != UserPostRes::class.java) return null
                    return UserPostConverter()
                }
            }
        }
    }
}

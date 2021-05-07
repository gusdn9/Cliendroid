package com.hyunwoo.cliendroid.network.converter.user

import com.hyunwoo.cliendroid.network.exception.RequiredLoginException
import com.hyunwoo.cliendroid.network.model.user.UserCommentItemDto
import com.hyunwoo.cliendroid.network.model.user.UserCommentRes
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class UserCommentConverter : Converter<ResponseBody, UserCommentRes> {

    override fun convert(value: ResponseBody): UserCommentRes? {
        val document = Jsoup.parse(value.string())

        val notLogin = document.getElementsByClass("not_login").size > 0
        if (notLogin) throw RequiredLoginException()

        val endOfPage = document.getElementsByClass("board-nav-next").size <= 0
        val comments = ArrayList<UserCommentItemDto>()

        document.getElementsByClass("list_item").forEach { item ->
            val subject = item.select(".list_subject span")
            val boardName = subject[0].text()
            val title = subject[1].text()
            val likes = item.select(".list_symph").text()
            val time = item.selectFirst(".time").ownText()
            val link = item.select(".list_subject").attr("href")
            comments.add(
                UserCommentItemDto(
                    title = title,
                    boardName = boardName,
                    likes = likes,
                    time = time,
                    link = link
                )
            )
        }

        return UserCommentRes(comments, endOfPage)
    }

    companion object {
        fun create(): Converter.Factory {
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    if (type != UserCommentRes::class.java) return null
                    return UserCommentConverter()
                }
            }
        }
    }
}

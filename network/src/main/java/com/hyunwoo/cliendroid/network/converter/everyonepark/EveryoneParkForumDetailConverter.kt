package com.hyunwoo.cliendroid.network.converter.everyonepark

import com.hyunwoo.cliendroid.network.extension.textOrNull
import com.hyunwoo.cliendroid.network.model.everyonepark.BaseCommentDto
import com.hyunwoo.cliendroid.network.model.everyonepark.BlockedCommentDto
import com.hyunwoo.cliendroid.network.model.everyonepark.CommentDto
import com.hyunwoo.cliendroid.network.model.everyonepark.EveryoneParkForumDetailRes
import com.hyunwoo.cliendroid.network.model.user.UserDto
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class EveryoneParkForumDetailConverter : Converter<ResponseBody, EveryoneParkForumDetailRes> {
    override fun convert(value: ResponseBody): EveryoneParkForumDetailRes? {

        val document = Jsoup.parse(value.string())
        val bodyHtml = document.getElementsByClass("post_article").first().html()

        val title = document.selectFirst(".post_subject span").text()
        val userNickname = document.selectFirst(".post_view .nickname")?.text()
        val userNickImage = document.selectFirst(".nickimg img")?.attr("src")
        val author = UserDto(null, userNickname, userNickImage)

        val timeDiv = document.selectFirst(".post_time span")
        val postTime = if (timeDiv.select(".edit_time").size > 0) {
            timeDiv.selectFirst(".time").text()
        } else {
            timeDiv.text()
        }
        val hits = document.getElementsByClass("view_count").text()
        val likes = document.select(".symph_count strong").textOrNull()?.toLong() ?: 0L
        val authorIpAddress = document.getElementsByClass("author_ip").text()

        val comments: ArrayList<BaseCommentDto> = ArrayList()

        document.getElementsByClass("comment_row").forEach { reply ->
            val isReply = reply.select(".comment_row.re").size > 0
            val blocked = reply.select(".comment_row.blocked").size > 0
            if (blocked) {
                val contents = reply.text()
                comments.add(BlockedCommentDto(contents, isReply))
            } else {
                val id = reply.select(".comment_view").attr("data-comment-view").toLong()
                val contents = reply.selectFirst(".comment_content input").attr("value")
                val ipAddress = reply.selectFirst("span.ip_address").text()
                val time = reply.getElementById("time").ownText()
                val like = reply.getElementsByClass("comment_content_symph").text().toLong()

                val nickname = reply.selectFirst("span.nickname")?.text()
                val nickImg = reply.selectFirst("span.nickimg img")?.attr("src")
                val user = UserDto(null, nickname, nickImg)

                comments.add(CommentDto(id, contents, ipAddress, time, like, user, isReply))
            }
        }

        return EveryoneParkForumDetailRes(
            title = title,
            user = author,
            time = postTime,
            hits = hits,
            likes = likes,
            ipAddress = authorIpAddress,
            htmlBody = bodyHtml,
            comments = comments
        )
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

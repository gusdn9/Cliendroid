package com.hyunwoo.cliendroid.network.converter

import com.hyunwoo.cliendroid.network.model.BaseCommentDto
import com.hyunwoo.cliendroid.network.model.BlockedCommentDto
import com.hyunwoo.cliendroid.network.model.CommentDto
import com.hyunwoo.cliendroid.network.model.EveryoneParkForumDetailRes
import com.hyunwoo.cliendroid.network.model.UserDto
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class EveryoneParkForumDetailConverter : Converter<ResponseBody, EveryoneParkForumDetailRes> {
    override fun convert(value: ResponseBody): EveryoneParkForumDetailRes? {

        val document = Jsoup.parse(value.string())
        val body = document.getElementsByClass("post_article").first().html()

        val comments: ArrayList<BaseCommentDto> = ArrayList()

        document.getElementsByClass("comment_row").forEach { reply ->
            val isReply = reply.select(".comment_row.re").size > 0
            val blocked = reply.select(".comment_row.blocked.re").size > 0
            if(blocked) {
                val contents = reply.text()
                comments.add(BlockedCommentDto(contents, isReply))
            } else {
                val id = reply.select(".comment_view").attr("data-comment-view").toLong()
                val contents = reply.selectFirst(".comment_content input").attr("value")
                val ipAddress = reply.selectFirst("span.ip_address").text()
                val time = reply.selectFirst("span.timestamp").text()

                val nickname = reply.selectFirst("span.nickname")?.text()
                val nickImg = reply.selectFirst("span.nickimg img")?.attr("src")
                val user = UserDto(null, nickname, nickImg)

                comments.add(CommentDto(id, contents, ipAddress, time, user, isReply))
            }
        }

        return EveryoneParkForumDetailRes(body, comments)
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

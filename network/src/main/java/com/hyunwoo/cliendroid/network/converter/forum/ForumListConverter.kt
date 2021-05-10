package com.hyunwoo.cliendroid.network.converter.forum

import com.hyunwoo.cliendroid.network.extension.parseLargeNumber
import com.hyunwoo.cliendroid.network.extension.textOrNull
import com.hyunwoo.cliendroid.network.model.forum.BaseListItemDto
import com.hyunwoo.cliendroid.network.model.forum.BlockedForumItemDto
import com.hyunwoo.cliendroid.network.model.forum.ForumItemDto
import com.hyunwoo.cliendroid.network.model.forum.ForumListRes
import com.hyunwoo.cliendroid.network.model.user.UserDto
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ForumListConverter : Converter<ResponseBody, ForumListRes> {

    override fun convert(value: ResponseBody): ForumListRes {

        val list: ArrayList<BaseListItemDto> = ArrayList()
        val document = Jsoup.parse(value.string())
        document.getElementsByClass("list_item").forEach { element ->
            val isNotice = element.hasClass("notice")
            val isBlocked = element.hasClass("blocked")

            if (isBlocked) {
                val id = element.selectFirst(".singo_comments")
                    .attr("id")
                    .substringAfterLast("_")
                    .toLong()
                val title = element.selectFirst(".list_title").text()
                list.add(BlockedForumItemDto(id, title))
            } else {
                val id = if (isNotice) {
                    element.select(".notice")
                        .attr("onclick")
                        .replace("'", "")
                        .substringAfterLast("/")
                } else {
                    element.attr("data-board-sn")
                }
                val nickNameClass = element.getElementsByClass("nickname")
                val user = UserDto(
                    null,
                    nickNameClass.textOrNull(),
                    element.selectFirst("img")?.attr("src")
                )

                val subject = element.getElementsByClass("list_subject")
                val title = if (isNotice) {
                    element.getElementsByClass("list_subject").text()
                } else {
                    element.getElementsByAttribute("title").attr("title")
                }
                val link = subject.attr("href").substringBefore("?")
                val replyCount = element.getElementsByClass("list_reply")
                    .select("span").first()?.text()?.parseLargeNumber()
                val hit = element.getElementsByClass("list_hit").textOrNull()?.parseLargeNumber()
                val time = element.getElementsByClass("list_time").text()
                val likes = element.selectFirst(".list_number .list_symph")?.text()?.parseLargeNumber()
                list.add(
                    ForumItemDto(
                        id.toLong(),
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
        }
        return ForumListRes(list)
    }

    companion object {
        fun create(): Converter.Factory {
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    if (type != ForumListRes::class.java) return null
                    return ForumListConverter()
                }
            }
        }
    }
}

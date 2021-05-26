package com.hyunwoo.cliendroid.network.converter.search

import com.hyunwoo.cliendroid.network.exception.RequiredLoginException
import com.hyunwoo.cliendroid.network.extension.parseLargeNumber
import com.hyunwoo.cliendroid.network.model.search.auth.BaseSearchAuthItemDto
import com.hyunwoo.cliendroid.network.model.search.auth.BlockedSearchAuthItemDto
import com.hyunwoo.cliendroid.network.model.search.auth.SearchAuthItemDto
import com.hyunwoo.cliendroid.network.model.search.auth.SearchAuthRes
import com.hyunwoo.cliendroid.network.model.user.UserDto
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

        document.select(".list_item").forEach { element ->
            val isBlocked = element.hasClass("blocked")
            if (isBlocked) {
                val id = element.selectFirst(".singo_comments")
                    .attr("id")
                    .substringAfterLast("_")
                    .toLong()
                val title = element.selectFirst(".list_title").text()
                list.add(BlockedSearchAuthItemDto(id, title))
            } else {
                val id = element.attr("data-board-sn").toLong()
                val title = element.selectFirst(".list_subject span").text()
                val replyCount = element.selectFirst(".list_reply")?.text()?.toLong() ?: 0L
                val likes = element.selectFirst(".list_symph")?.text()?.toLong() ?: 0L
                val board = element.selectFirst(".shortname").text()
                val link = element.selectFirst(".list_subject").attr("href")
                val time = element.selectFirst(".list_time").text()
                val hits = element.selectFirst(".list_hit").text().parseLargeNumber()
                val author = element.selectFirst(".list_author")
                val userId = element.attr("data-author-id")
                val userNickname = author.selectFirst(".nickname")?.text()
                val userImage = author.selectFirst(".nickimg img")?.attr("src")

                list.add(
                    SearchAuthItemDto(
                        id = id,
                        title = title,
                        replyCount = replyCount,
                        likes = likes,
                        board = board,
                        link = link,
                        time = time,
                        hits = hits,
                        user = UserDto(id = userId, nickName = userNickname, image = userImage)
                    )
                )
            }
        }
        val endOfPage = document.getElementsByClass("board-nav-next").size <= 0

        return SearchAuthRes(list, endOfPage)
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

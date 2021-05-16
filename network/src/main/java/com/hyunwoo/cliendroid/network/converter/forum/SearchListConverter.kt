package com.hyunwoo.cliendroid.network.converter.forum

import com.hyunwoo.cliendroid.network.extension.parseLargeNumber
import com.hyunwoo.cliendroid.network.extension.textOrNull
import com.hyunwoo.cliendroid.network.model.search.withoutauth.BaseSearchItemDto
import com.hyunwoo.cliendroid.network.model.search.withoutauth.BlockedSearchItemDto
import com.hyunwoo.cliendroid.network.model.search.withoutauth.BoardItemDto
import com.hyunwoo.cliendroid.network.model.search.withoutauth.SearchItemDto
import com.hyunwoo.cliendroid.network.model.search.withoutauth.SearchRes
import com.hyunwoo.cliendroid.network.model.user.UserDto
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SearchListConverter : Converter<ResponseBody, SearchRes> {
    override fun convert(value: ResponseBody): SearchRes {

        val list: ArrayList<BaseSearchItemDto> = ArrayList()
        val boardList: ArrayList<BoardItemDto> = ArrayList()
        val document = Jsoup.parse(value.string())
        document.select("#selectBoardCd option").forEach { option ->
            val id = option.attr("value")
            val name = option.text()
            boardList.add(BoardItemDto(id, name))
        }

        document.select(".list_item").forEach { element ->
            val isBlocked = element.hasClass("blocked")
            if (isBlocked) {
                val id = element.selectFirst(".singo_comments")
                    .attr("id")
                    .substringAfterLast("_")
                    .toLong()
                val title = element.selectFirst(".list_title").text()
                list.add(BlockedSearchItemDto(id, title))
            } else {
                val id = element.attr("data-board-sn")
                val onclick = element.selectFirst(".search").attr("onclick")
                val regex = "'(.*?)'".toRegex()
                lateinit var boardId: String
                lateinit var boardName: String
                for ((i, result) in regex.findAll(onclick).iterator().withIndex()) {
                    when (i) {
                        0 -> boardId = result.value
                        1 -> boardName = result.value
                    }
                }
                val board = BoardItemDto(boardId, boardName)

                val subject = element.selectFirst(".list_subject")
                val title = subject.text()
                val link = subject.attr("href").substringBefore("?")
                val summary = element.selectFirst(".preview_search").text()
                val time = element.selectFirst(".list_time").text()
                val hits = element.selectFirst(".list_hit")?.text()?.parseLargeNumber() ?: 0L
                val nickNameClass = element.getElementsByClass("nickname")
                val user = UserDto(
                    null,
                    nickNameClass.textOrNull(),
                    element.selectFirst("img")?.attr("src")
                )
                list.add(
                    SearchItemDto(
                        id = id.toLong(),
                        board = board,
                        title = title,
                        summary = summary,
                        link = link,
                        time = time,
                        hit = hits,
                        user = user
                    )
                )
            }
        }
        val endOfPage = document.getElementsByClass("board-nav-next").size <= 0
        return SearchRes(list, boardList, endOfPage)
    }

    companion object {
        fun create(): Converter.Factory {
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    if (type != SearchRes::class.java) return null
                    return SearchListConverter()
                }
            }
        }
    }
}

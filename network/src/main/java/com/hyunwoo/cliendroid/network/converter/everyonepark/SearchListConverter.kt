package com.hyunwoo.cliendroid.network.converter.everyonepark

import com.hyunwoo.cliendroid.network.model.BoardDto
import com.hyunwoo.cliendroid.network.model.SearchItemDto
import com.hyunwoo.cliendroid.network.model.SearchListRes
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SearchListConverter : Converter<ResponseBody, SearchListRes> {
    override fun convert(value: ResponseBody): SearchListRes? {

        val list: ArrayList<SearchItemDto> = ArrayList()
        val document = Jsoup.parse(value.string())
        document.select(".list_item").forEach { element ->
            val onclick = element.selectFirst(".search").attr("onclick")
            val regex = "'(.*?)'".toRegex()
            lateinit var boardId: String
            lateinit var boardName: String
            for ((i, result) in regex.findAll(onclick).iterator().withIndex()) {
                when(i) {
                    0 -> boardId = result.value
                    1 -> boardName = result.value
                }
            }
            val board = BoardDto(boardId, boardName)

            val subject = element.selectFirst(".list_subject")
            val title = subject.text()
            val link = subject.attr("href")
            val time = element.selectFirst(".list_time").text()
            val hits = element.selectFirst(".list_hit").text()
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
                    if (type != SearchListRes::class.java) return null
                    return SearchListConverter()
                }
            }
        }
    }
}

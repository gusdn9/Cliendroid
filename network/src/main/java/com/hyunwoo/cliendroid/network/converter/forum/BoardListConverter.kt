package com.hyunwoo.cliendroid.network.converter.forum

import com.hyunwoo.cliendroid.network.model.BoardRes
import com.hyunwoo.cliendroid.network.model.MenuBoardDto
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class BoardListConverter : Converter<ResponseBody, BoardRes> {
    override fun convert(value: ResponseBody): BoardRes? {
        val document = Jsoup.parse(value.string())
        val communities: ArrayList<MenuBoardDto> = ArrayList()
        document.select(".navigation .snb_navmenu .menu-list").forEach { menu ->
            val link = menu.select("a").attr("href")
            val name = menu.selectFirst(".menu_over").text()
            communities.add(MenuBoardDto(name, link))
        }

        val somoim: ArrayList<MenuBoardDto> = ArrayList()
        document.select(".navigation .snb_groupmenu .menu-list").forEach { menu ->
            val link = menu.select("a").attr("href")
            val name = menu.selectFirst(".menu_over").text()
            somoim.add(MenuBoardDto(name, link))
        }
        return BoardRes(communities, somoim)
    }

    companion object {
        fun create(): Converter.Factory {
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    if (type != BoardRes::class.java) return null
                    return BoardListConverter()
                }
            }
        }
    }
}

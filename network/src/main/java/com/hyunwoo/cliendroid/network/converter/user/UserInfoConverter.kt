package com.hyunwoo.cliendroid.network.converter.user

import com.hyunwoo.cliendroid.network.exception.RequiredLoginException
import com.hyunwoo.cliendroid.network.model.user.UserInfoRes
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class UserInfoConverter : Converter<ResponseBody, UserInfoRes> {
    override fun convert(value: ResponseBody): UserInfoRes? {
        val document = Jsoup.parse(value.string())

        val notLogin = document.getElementsByClass("not_login").size > 0
        if (notLogin) throw RequiredLoginException()

        val nickName = document.selectFirst(".nicktext").text()

        val userActivities = document.select(".user_activity li")
        var startDate: String? = null
        var email: String? = null
        userActivities.forEach { activity ->
            try {
                val span = activity.select("span")
                val title = span[0].text()
                if (title.contains("가입일")) {
                    startDate = span[1]?.text()
                } else if (title.contains("이메일")) {
                    email = activity.selectFirst("a")?.text()
                }
            } catch (e: Exception) {
            }
        }
        return UserInfoRes(nickName, startDate, email)
    }

    companion object {
        fun create(): Converter.Factory {
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    if (type != UserInfoRes::class.java) return null
                    return UserInfoConverter()
                }
            }
        }
    }
}

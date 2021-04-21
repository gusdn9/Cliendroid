package com.hyunwoo.cliendroid.network.converter.auth

import com.hyunwoo.cliendroid.network.model.LoginRes
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class LoginConverter : Converter<ResponseBody, LoginRes> {
    override fun convert(value: ResponseBody): LoginRes? {
        val document = Jsoup.parse(value.string())
        // body가 비어서 오면 로그인 성공한 것으로 판단.
        val result = document.body().childrenSize() < 1
        // TODO 메모 리스트가 javascript에 들어가 있다. 추후에 webview 등으로 꺼내야 하지 않을까
        val script = document.select("script").first()

        return LoginRes(result)
    }

    companion object {
        fun create(): Converter.Factory {
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    if (type != LoginRes::class.java) return null
                    return LoginConverter()
                }
            }
        }
    }
}

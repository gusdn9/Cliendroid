package net.meshkorea.android.network.converter

import net.meshkorea.android.network.model.EveryOneParkForumDto
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class EveryOneParkForumConverter : Converter<ResponseBody, List<EveryOneParkForumDto>> {

    override fun convert(value: ResponseBody): List<EveryOneParkForumDto>? {
        val document = Jsoup.parse(value.toString())

        return null
    }

    companion object {
        fun create(): Converter.Factory {
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *> {
                    return EveryOneParkForumConverter()
                }
            }
        }
    }
}

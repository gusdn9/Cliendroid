package com.hyunwoo.cliendroid.network.model.forum

import com.squareup.moshi.Json

data class CommentWriteReq(
    @Json(name = "boardSn") val boardSn: String,
    @Json(name = "param") val param: Param
) {
    data class Param(
        @Json(name = "comment") val comment: String,
        @Json(name = "images") val images: List<String>,
        @Json(name = "articleRegister") val articleRegister: String
    )
}

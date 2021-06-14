package com.hyunwoo.cliendroid.network.model.forum

data class CommentWriteReq(
    val boardSn: String,
    val param: Param
) {
    data class Param(
        val comment: String,
        val images: List<String>,
        val articleRegister: String
    )
}



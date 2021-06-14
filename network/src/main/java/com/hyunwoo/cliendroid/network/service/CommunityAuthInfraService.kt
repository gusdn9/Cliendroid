package com.hyunwoo.cliendroid.network.service

import com.hyunwoo.cliendroid.network.model.forum.CommentWriteReq
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CommunityAuthInfraService {

    @POST("/board/{boardCd}/{boardSn}/comment/regist")
    suspend fun commentWrite(
        @Path("boardCd") boardCd: String,
        @Path("boardSn") boardSn: String,
        @Body req: CommentWriteReq
    )
}

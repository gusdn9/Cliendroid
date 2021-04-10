package com.hyunwoo.cliendroid.network

import com.hyunwoo.cliendroid.network.model.EveryoneParkForumDetailRes
import com.hyunwoo.cliendroid.network.model.EveryoneParkForumListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityInfraService {

    @GET("service/board/park")
    suspend fun getEveryoneParkForumList(@Query("po") page: Int): List<EveryoneParkForumListDto>

    @GET("service/board/park/{forumId}")
    suspend fun getEveryoneParkForumDetail(
        @Path("forumId") forumId: Int
    ): EveryoneParkForumDetailRes
}

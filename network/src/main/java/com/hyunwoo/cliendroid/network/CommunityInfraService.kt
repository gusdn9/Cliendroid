package com.hyunwoo.cliendroid.network

import com.hyunwoo.cliendroid.network.model.EveryoneParkForumDetailRes
import com.hyunwoo.cliendroid.network.model.EveryoneParkForumListRes
import com.hyunwoo.cliendroid.network.model.SearchListRes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityInfraService {

    @GET("service/board/park")
    suspend fun getEveryoneParkForumList(@Query("po") page: Int): EveryoneParkForumListRes

    @GET("{detailUrl}")
    suspend fun getEveryoneParkForumDetail(
        @Path("detailUrl", encoded = true) detailUrl: String
    ): EveryoneParkForumDetailRes

    @GET("service/search")
    suspend fun search(
        @Query("q") keyword: String,
        @Query("p") page: Int = 0,
        @Query("sort") sort: String? = null,
        @Query("boardCd") boardId: String? = null
    ): SearchListRes
}

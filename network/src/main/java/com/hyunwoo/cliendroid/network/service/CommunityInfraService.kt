package com.hyunwoo.cliendroid.network.service

import com.hyunwoo.cliendroid.network.model.BoardRes
import com.hyunwoo.cliendroid.network.model.forum.ForumDetailRes
import com.hyunwoo.cliendroid.network.model.forum.ForumListRes
import com.hyunwoo.cliendroid.network.model.search.withoutauth.SearchRes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityInfraService {

    @GET("{boardLink}")
    suspend fun getForumList(
        @Path("boardLink", encoded = true) link: String,
        @Query("po") page: Int
    ): ForumListRes

    @GET("{detailUrl}")
    suspend fun getForumDetail(
        @Path("detailUrl", encoded = true) detailUrl: String
    ): ForumDetailRes

    @GET("service/search")
    suspend fun search(
        @Query("q") keyword: String,
        @Query("p") page: Int = 0,
        @Query("sort") sort: String? = null,
        @Query("boardCd") boardId: String? = null
    ): SearchRes

    @GET("service/")
    suspend fun getMenuList(): BoardRes
}

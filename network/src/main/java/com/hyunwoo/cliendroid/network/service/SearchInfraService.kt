package com.hyunwoo.cliendroid.network.service

import com.hyunwoo.cliendroid.network.model.search.auth.SearchAuthRes
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchInfraService {

    @GET("service/search/group/clien_all")
    suspend fun search(
        @Query("sk") target: String,
        @Query("sv") keyword: String,
        @Query("po") page: Int
    ): SearchAuthRes
}

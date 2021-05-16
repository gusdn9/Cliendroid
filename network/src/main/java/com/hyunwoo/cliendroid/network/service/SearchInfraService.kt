package com.hyunwoo.cliendroid.network.service

import retrofit2.http.Query

interface SearchInfraService {

    suspend fun search(
        @Query("sk") target: String,
        @Query("sv") keyword: String,
        @Query("po") page: Int
    )

}

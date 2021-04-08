package com.hyunwoo.cliendroid.network

import com.hyunwoo.cliendroid.network.model.EveryoneParkForumDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityInfraService {

    @GET("service/board/park")
    suspend fun getEveryoneParkForum(@Query("po") page: Int): List<EveryoneParkForumDto>
}

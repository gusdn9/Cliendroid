package net.meshkorea.android.network

import net.meshkorea.android.network.model.EveryOneParkForumDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityInfraService {

    @GET("service/board/park")
    suspend fun getEveryOneParkForum(@Query("po") page: Int): Response<List<EveryOneParkForumDto>>
}

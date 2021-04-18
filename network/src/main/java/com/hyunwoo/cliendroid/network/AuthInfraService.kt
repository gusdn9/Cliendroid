package com.hyunwoo.cliendroid.network

import com.hyunwoo.cliendroid.network.model.LoginPreparedStatementRes
import com.hyunwoo.cliendroid.network.model.LoginRes
import com.hyunwoo.cliendroid.network.model.UserInfoRes
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthInfraService {

    /**
     * 로그인시 _csrf 값을 로그인페이지에서 확인을 하는데, 해당 값을 얻기 위한 api
     */
    @GET("service/auth/login")
    suspend fun loginPreparedStatement(): LoginPreparedStatementRes

    /**
     * 로그인
     */
    @FormUrlEncoded
    @POST("service/login")
    suspend fun login(
        @Field("userId") id: String,
        @Field("userPassword") password: String,
        @Field("_csrf") csrf: String,
    ): LoginRes

    @GET("service/popup/userInfo/basic/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: String
    ): UserInfoRes

    @GET("service/mypage/myInfoComfrim")
    suspend fun myPage(): Response<String>
}

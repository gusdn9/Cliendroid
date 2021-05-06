package com.hyunwoo.cliendroid.network

import com.hyunwoo.cliendroid.network.model.UserCommentRes
import com.hyunwoo.cliendroid.network.model.UserInfoRes
import com.hyunwoo.cliendroid.network.model.UserPostRes
import retrofit2.http.GET
import retrofit2.http.Path

interface UserInfraService {

    /**
     * 유저정보
     */
    @GET("service/popup/userInfo/basic/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): UserInfoRes

    /**
     * 게시글
     */
    @GET("service/popup/userInfo/posts/{userId}")
    suspend fun getUserPosts(@Path("userId") userId: String): UserPostRes

    /**
     * 댓글
     */
    @GET("service/popup/userInfo/comments/{userId}")
    suspend fun getUserComments(@Path("userId") userId: String): UserCommentRes

    /**
     * 공감글
     */
    @GET("service/popup/userInfo/symph_posts/{userId}")
    suspend fun getUserSympatheticPosts(@Path("userId") userId: String): UserPostRes

    /**
     * 공감댓글
     */
    @GET("service/popup/userInfo/symph_comments/{userId}")
    suspend fun getUserSympatheticComments(@Path("userId") userId: String): UserCommentRes
}

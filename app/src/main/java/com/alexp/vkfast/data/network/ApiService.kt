package com.alexp.vkfast.data.network

import com.alexp.vkfast.data.model.CommentsContentDto
import com.alexp.vkfast.data.model.CommentsResponseDto
import com.alexp.vkfast.data.model.FavouritesPostsResponseDto
import com.alexp.vkfast.data.model.LikesCountResponseDto
import com.alexp.vkfast.data.model.NewFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadRecommendations(
        @Query("access_token") token:String
    ):NewFeedResponseDto


    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadRecommendations(
        @Query("access_token") token:String,
        @Query("start_from") startFrom:String
    ):NewFeedResponseDto



    @GET("likes.add?v=5.199&type=post")
    suspend fun addLike(
        @Query("access_token") token : String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ) : LikesCountResponseDto

    @GET("likes.delete?v=5.199&type=post")
    suspend fun deleteLike(
        @Query("access_token") token : String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ) : LikesCountResponseDto

    @GET("newsfeed.ignoreItem?v=5.199&type=wall")
    suspend fun ignorePost(
        @Query("access_token") accessToken:String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    )

    @GET("wall.getComments?v=5.199&extended=1&fields=photo_200")

    suspend fun getComments(
        @Query("access_token") accessToken:String,
        @Query("owner_id") ownerId: Long,
        @Query("post_id") postId: Long
    ) : CommentsResponseDto


    @GET("fave.addPost?v=5.199")
    suspend fun addToFavouriteList(
        @Query("access_token") token : String,
        @Query("owner_id") ownerId: Long,
        @Query("id") postId: Long
    )

    @GET("fave.removePost?v=5.199")
    suspend fun deleteFromFavouriteList(
        @Query("access_token") token : String,
        @Query("owner_id") ownerId: Long,
        @Query("id") postId: Long
    )



    @GET("fave.get?v=5.199&extended=1&fields=photo_200,name")
    suspend fun loadFavouritesPosts(
        @Query("access_token") token:String
    ): FavouritesPostsResponseDto


    @GET("fave.get?v=5.199&extended=1&fields=photo_200,name")
    suspend fun loadFavouritesPosts(
        @Query("access_token") token:String,
        @Query("start_from") startFrom:String
    ):FavouritesPostsResponseDto


}
package com.alexp.vkfast.domain.repository

import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.entity.NewsFeedResult
import com.alexp.vkfast.domain.entity.PostComment
import com.alexp.vkfast.presentation.main.AuthState
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getAuthStateFlow():StateFlow<AuthState>

    fun getRecommendations():StateFlow<NewsFeedResult>
    fun getAccessToken(): String
    fun getComments(newsItem: NewsItem): StateFlow<List<PostComment>>


    //authorization
    suspend fun authorizeUser()


    //news
    suspend fun loadMoreNews()
    suspend fun updateLikeStatus(newsItem: NewsItem)
    suspend fun removeNewsItem(newsItem: NewsItem)


    //faves
    suspend fun updateFavouriteStatus(newsItem: NewsItem)
    fun getFavourites():StateFlow<NewsFeedResult>
    suspend fun loadMoreFavouritePost()


}
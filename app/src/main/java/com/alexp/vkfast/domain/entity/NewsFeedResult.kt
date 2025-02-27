package com.alexp.vkfast.domain.entity

sealed class NewsFeedResult{


    data object Error: NewsFeedResult()

    data class Success(val post: List<NewsItem>) : NewsFeedResult()
}
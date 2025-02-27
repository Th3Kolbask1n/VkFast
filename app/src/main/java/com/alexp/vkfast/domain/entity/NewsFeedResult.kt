package com.alexp.vkfast.domain.entity

sealed class NewsFeedResult{


    object Error: NewsFeedResult()

    data class Success(val post: List<NewsItem>) : NewsFeedResult()
}
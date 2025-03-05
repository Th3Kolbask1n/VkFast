package com.alexp.vkfast.presentation.news

import com.alexp.vkfast.domain.entity.NewsItem

sealed class NewsFeedScreenState {
    object Initial : NewsFeedScreenState()

    object Loading : NewsFeedScreenState()

    data class Posts(
        val posts: List<NewsItem>,
        val nextDataIsLoading: Boolean
    ) : NewsFeedScreenState()

    data class Error(val message: String) : NewsFeedScreenState()

    object Empty : NewsFeedScreenState()
}

package com.alexp.vkfast.presentation.favourites

import com.alexp.vkfast.domain.entity.NewsItem

sealed class FavouritePostsScreenState {
    object Initial : FavouritePostsScreenState()

    object Loading : FavouritePostsScreenState()

    data class Posts(
        val posts: List<NewsItem>,
        val nextDataIsLoading: Boolean = false
    ) : FavouritePostsScreenState()

    data class Error(val message: String) : FavouritePostsScreenState()

    object Empty : FavouritePostsScreenState()
}

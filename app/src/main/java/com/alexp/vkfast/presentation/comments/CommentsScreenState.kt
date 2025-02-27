package com.alexp.vkfast.presentation.comments

import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.entity.PostComment

sealed class CommentsScreenState {

    object Initial: CommentsScreenState()


    data class Comments(val newsItem: NewsItem, val comments: List<PostComment>) : CommentsScreenState()
}
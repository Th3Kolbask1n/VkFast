package com.alexp.vkfast.domain.usecases

import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.entity.PostComment
import com.alexp.vkfast.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetCommentsUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(newsItem: NewsItem) : StateFlow<List<PostComment>>{
        return repository.getComments(newsItem = newsItem)
    }
}
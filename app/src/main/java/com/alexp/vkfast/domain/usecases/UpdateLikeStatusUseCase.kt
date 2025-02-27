package com.alexp.vkfast.domain.usecases

import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.repository.NewsFeedRepository

class UpdateLikeStatusUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(newsItem: NewsItem){
        return repository.updateLikeStatus(newsItem)
    }
}
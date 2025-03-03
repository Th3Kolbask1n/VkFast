package com.alexp.vkfast.domain.usecases

import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.repository.NewsFeedRepository
import javax.inject.Inject

class UpdateFavouriteStatusUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(newsItem: NewsItem){
        return repository.updateFavouriteStatus(newsItem)
    }
}
package com.alexp.vkfast.domain.usecases

import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.entity.NewsFeedResult
import com.alexp.vkfast.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetRecommendationsUseCase@Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke() : StateFlow<NewsFeedResult>{
        return repository.getRecommendations()
    }
}
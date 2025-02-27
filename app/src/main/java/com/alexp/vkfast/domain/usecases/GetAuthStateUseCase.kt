package com.alexp.vkfast.domain.usecases

import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.entity.PostComment
import com.alexp.vkfast.domain.repository.NewsFeedRepository
import com.alexp.vkfast.presentation.main.AuthState
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke() : StateFlow<AuthState>{
        return repository.getAuthStateFlow()
    }
}
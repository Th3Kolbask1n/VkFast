package com.alexp.vkfast.domain.usecases

import com.alexp.vkfast.domain.repository.NewsFeedRepository

class LoadNextDataUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(){
        return repository.loadMoreNews()
    }
}
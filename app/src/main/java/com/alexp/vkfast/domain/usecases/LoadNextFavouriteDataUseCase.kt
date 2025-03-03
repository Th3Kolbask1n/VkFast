package com.alexp.vkfast.domain.usecases

import com.alexp.vkfast.domain.repository.NewsFeedRepository
import javax.inject.Inject

class LoadNextFavouriteDataUseCase@Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(){
        return repository.loadMoreNews()
    }
}
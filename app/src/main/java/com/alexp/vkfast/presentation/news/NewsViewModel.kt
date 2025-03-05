package com.alexp.vkfast.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexp.vkfast.R
import com.alexp.vkfast.data.repository.NewsFeedRepositoryImpl
import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.entity.NewsFeedResult
import com.alexp.vkfast.domain.usecases.UpdateLikeStatusUseCase
import com.alexp.vkfast.domain.usecases.DeletePostUseCase
import com.alexp.vkfast.domain.usecases.GetRecommendationsUseCase
import com.alexp.vkfast.domain.usecases.LoadNextDataUseCase
import com.alexp.vkfast.domain.usecases.UpdateFavouriteStatusUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val updateLikeStatusUseCase: UpdateLikeStatusUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val updateFavouriteStatusUseCase: UpdateFavouriteStatusUseCase
) : ViewModel() {

    private val newsFlow = getRecommendationsUseCase()
    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsFeedViewModel", "Exception caught")
    }

    val screenState = merge(
        newsFlow.map { result ->
            when (result) {
                is NewsFeedResult.Success -> {
                    if (result.post.isNotEmpty()) {
                        NewsFeedScreenState.Posts(posts = result.post,false)
                    } else {
                        NewsFeedScreenState.Loading
                    }
                }
                is NewsFeedResult.Error -> NewsFeedScreenState.Error("Can't load")
            }
        },
        loadNextDataFlow
    )
        .onStart { emit(NewsFeedScreenState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = NewsFeedScreenState.Loading
        )

    fun loadNextRecommendations() {
        viewModelScope.launch {
            val currentPosts = (newsFlow.firstOrNull() as? NewsFeedResult.Success)?.post ?: emptyList()
            loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    posts = currentPosts,
                    nextDataIsLoading = true
                )
            )

            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(newsItem: NewsItem) {
        viewModelScope.launch(exceptionHandler) {
            updateLikeStatusUseCase(newsItem)
        }
    }

    fun remove(newsItem: NewsItem) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(newsItem)
        }
    }

    fun changeFavouriteStatus(newsItem: NewsItem) {
        viewModelScope.launch(exceptionHandler) {
            updateFavouriteStatusUseCase(newsItem)
        }
    }
}

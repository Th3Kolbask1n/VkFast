package com.alexp.vkfast.presentation.favourites

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
import com.alexp.vkfast.domain.usecases.GetFavouritesUseCase
import com.alexp.vkfast.domain.usecases.GetRecommendationsUseCase
import com.alexp.vkfast.domain.usecases.LoadNextDataUseCase
import com.alexp.vkfast.domain.usecases.LoadNextFavouriteDataUseCase
import com.alexp.vkfast.domain.usecases.UpdateFavouriteStatusUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritePostsViewModel @Inject constructor
    (

    private val getFavouritePostUseCase: GetFavouritesUseCase,
    private val loadNextDataUseCase: LoadNextFavouriteDataUseCase,
    private val updateLikeStatusUseCase: UpdateLikeStatusUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val updateFavouriteStatusUseCase: UpdateFavouriteStatusUseCase

) : ViewModel() {

    private val newsFlow = getFavouritePostUseCase()

    private val loadNextDataFlow = MutableSharedFlow<FavouritePostsScreenState>()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("FavouritePostsViewModel", "Exception caught")
    }


    val screenState = newsFlow
        .map { result ->
            when (result) {
                is NewsFeedResult.Success -> {
                    if (result.post.isNotEmpty()) {
                        FavouritePostsScreenState.Posts(posts = result.post)
                    } else {
                        FavouritePostsScreenState.Empty
                    }
                }

                is NewsFeedResult.Error -> FavouritePostsScreenState.Error(
                    "Application.applicationContext.getString(R.string.cant_loading_posts)"
                )
            }
        }
        .onStart { emit(FavouritePostsScreenState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = FavouritePostsScreenState.Loading
        )


    fun loadNextRecommendations() {
        viewModelScope.launch {
            val currentPosts =
                (newsFlow.firstOrNull() as? NewsFeedResult.Success)?.post ?: emptyList()
            loadNextDataFlow.emit(
                FavouritePostsScreenState.Posts(
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

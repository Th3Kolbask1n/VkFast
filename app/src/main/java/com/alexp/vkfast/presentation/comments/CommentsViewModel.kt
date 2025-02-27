package com.alexp.vkfast.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.alexp.vkfast.data.repository.NewsFeedRepositoryImpl
import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    newsItem: NewsItem,
    application: Application
): ViewModel() {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getCommentsUseCase = GetCommentsUseCase(repository)

    val screenState = getCommentsUseCase(newsItem)
        .map { CommentsScreenState.Comments(
            newsItem = newsItem,
            comments = it
        ) }


}
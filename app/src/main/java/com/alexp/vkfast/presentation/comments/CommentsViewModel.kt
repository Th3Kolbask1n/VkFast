package com.alexp.vkfast.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.alexp.vkfast.data.repository.NewsFeedRepositoryImpl
import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentsViewModel@Inject constructor(
    private val newsItem: NewsItem,
    private val getCommentsUseCase : GetCommentsUseCase

): ViewModel() {



    val screenState = getCommentsUseCase(newsItem)
        .map { CommentsScreenState.Comments(
            newsItem = newsItem,
            comments = it
        ) }


}
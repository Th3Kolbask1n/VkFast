package com.alexp.vkfast.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexp.vkfast.domain.entity.NewsItem

class CommentsViewModelFactory(
    private val newsItem: NewsItem,
    private val application: Application
) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(newsItem,application) as T
    }
}
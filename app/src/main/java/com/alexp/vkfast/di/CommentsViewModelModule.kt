package com.alexp.vkfast.di

import androidx.lifecycle.ViewModel
import com.alexp.vkfast.presentation.comments.CommentsViewModel
import com.alexp.vkfast.presentation.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface CommentsViewModelModule {

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(viewModel: CommentsViewModel):ViewModel
}
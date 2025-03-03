package com.alexp.vkfast.di

import androidx.lifecycle.ViewModel
import com.alexp.vkfast.presentation.comments.CommentsViewModel
import com.alexp.vkfast.presentation.favourites.FavouritePostsViewModel
import com.alexp.vkfast.presentation.main.MainViewModel
import com.alexp.vkfast.presentation.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel):ViewModel




    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    @Binds
    fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FavouritePostsViewModel::class)
    @Binds
    fun bindFavouritePostsViewModel(viewModel: FavouritePostsViewModel): ViewModel

}
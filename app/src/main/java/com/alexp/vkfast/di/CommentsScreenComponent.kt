package com.alexp.vkfast.di

import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent(
    modules = [
        CommentsViewModelModule::class
    ]
)
interface CommentsScreenComponent {

    fun getViewModelFactory() : ViewModelFactory


    @Subcomponent.Factory
    interface Factory{

        fun create(
            @BindsInstance newsItem: NewsItem
        ): CommentsScreenComponent
    }
}
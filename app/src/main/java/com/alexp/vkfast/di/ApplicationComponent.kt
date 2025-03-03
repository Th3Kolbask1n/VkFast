package com.alexp.vkfast.di

import android.content.Context
import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.presentation.ViewModelFactory
import com.alexp.vkfast.presentation.main.MainActivity
import dagger.Binds
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(
    modules = [
        DataModule::class,
    ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory():ViewModelFactory

    fun getCommentsScreenComponentFactory():CommentsScreenComponent.Factory
    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance context:Context
        ):ApplicationComponent
    }
}
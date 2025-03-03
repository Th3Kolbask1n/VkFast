package com.alexp.vkfast.di

import com.alexp.vkfast.data.network.ApiFactory
import com.alexp.vkfast.data.network.ApiService
import com.alexp.vkfast.data.repository.NewsFeedRepositoryImpl
import com.alexp.vkfast.domain.repository.NewsFeedRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl:NewsFeedRepositoryImpl): NewsFeedRepository


    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService():ApiService{
            return ApiFactory.apiService
        }

    }

}
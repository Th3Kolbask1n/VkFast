package com.alexp.vkfast.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alexp.vkfast.data.repository.NewsFeedRepositoryImpl
import com.alexp.vkfast.domain.usecases.GetAuthStateUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application){


    private val repository = NewsFeedRepositoryImpl(application)

    private val getAuthStateUseCase = GetAuthStateUseCase(repository)
    val authState = getAuthStateUseCase()


    init {
        authorizeUser()
    }

    fun authorizeUser() {
        viewModelScope.launch {
            repository.authorizeUser()
        }
    }

}
package com.alexp.vkfast.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexp.vkfast.data.repository.NewsFeedRepositoryImpl
import com.alexp.vkfast.domain.usecases.AuthorizeUseCase
import com.alexp.vkfast.domain.usecases.GetAuthStateUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel@Inject constructor
    (
    private val getAuthStateUseCase : GetAuthStateUseCase,
    private val authorizeUseCase : AuthorizeUseCase


) : ViewModel(){


    val authState = getAuthStateUseCase()


    init {
        authorizeUser()
    }

    fun authorizeUser() {
        viewModelScope.launch {
            authorizeUseCase()
        }
    }

}
package com.alexp.vkfast.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexp.vkfast.di.ApplicationScope
import dagger.Provides
import javax.inject.Provider
import javax.inject.Inject


class ViewModelFactory @Inject constructor(
    private val viewModelProviders:@JvmSuppressWildcards  Map<Class<out ViewModel>, Provider<ViewModel>>
)
    :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}
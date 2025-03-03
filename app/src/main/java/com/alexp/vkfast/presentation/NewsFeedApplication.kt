package com.alexp.vkfast.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.alexp.vkfast.di.ApplicationComponent
import com.alexp.vkfast.di.DaggerApplicationComponent
import com.alexp.vkfast.domain.entity.NewsItem

class NewsFeedApplication : Application() {

    val component:ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this
        )
    }
}


@Composable
fun getApplicationComponent():ApplicationComponent
{
    return (LocalContext.current.applicationContext as NewsFeedApplication).component
}
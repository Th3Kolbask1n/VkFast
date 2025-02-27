package com.alexp.vkfast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.alexp.vkfast.domain.entity.NewsItem

fun NavGraphBuilder.homeScreenNavGraph (
    newsFeedScreenContent:@Composable () -> Unit,
    commentScreenContent: @Composable (NewsItem)-> Unit

    ){

    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    )
    {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEEDPOST){
                    type = NewsItem.NavigationType
                }
            )
        ) {
                val newsItem = it.arguments?.getParcelable<NewsItem>(Screen.KEY_FEEDPOST)?:
                throw RuntimeException("arguments is null")
            commentScreenContent(newsItem)
        }
    }
}
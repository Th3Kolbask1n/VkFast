package com.alexp.vkfast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alexp.vkfast.domain.entity.NewsItem

@Composable

fun AppNavGraph(navHostController: NavHostController,
                newsFeedScreenContent:@Composable () -> Unit,
                favouriteScreenContent:@Composable () -> Unit,
                profileScreenContent:@Composable () -> Unit,
                commentScreenContent: @Composable (NewsItem)-> Unit)
{

    NavHost(navController = navHostController,
        startDestination = Screen.Home.route,
        builder = {

            homeScreenNavGraph(
                newsFeedScreenContent = newsFeedScreenContent,
                commentScreenContent = commentScreenContent
            )
            composable(Screen.Favourite.route) {
                favouriteScreenContent()
            }
            composable(Screen.Profile.route) {
                profileScreenContent()
            }
        }
    )
}
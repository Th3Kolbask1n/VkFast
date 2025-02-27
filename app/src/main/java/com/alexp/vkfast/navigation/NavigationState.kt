package com.alexp.vkfast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alexp.vkfast.domain.entity.NewsItem

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {


        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    }

    fun navigateToComments(newsItem: NewsItem) {

        navHostController.navigate(Screen.Comments.getRouteWithArgs(newsItem))
    }
}


@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()

): NavigationState {

    return remember {
        NavigationState(navHostController)
    }
}
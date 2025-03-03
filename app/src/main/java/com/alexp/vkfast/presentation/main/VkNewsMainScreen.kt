package com.alexp.vkfast.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alexp.vkfast.navigation.AppNavGraph
import com.alexp.vkfast.navigation.rememberNavigationState
import com.alexp.vkfast.presentation.ViewModelFactory
import com.alexp.vkfast.presentation.comments.CommentsScreen
import com.alexp.vkfast.presentation.favourites.FavouritePostsScreen
import com.alexp.vkfast.presentation.news.NewsFeedScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable

fun MainScreen() {

    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            NavigationBar()
            {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )

                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.hierarchy?.any{
                        it.route == item.screen.route
                    }?: false
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if(!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    )
    { paddingValues ->
        AppNavGraph(navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                    NewsFeedScreen(
                        paddingValues = paddingValues,
                        onCommentClickListener = {
                            navigationState.navigateToComments(it)
                        }
                    )

            },
            favouriteScreenContent = {
                FavouritePostsScreen(
                    paddingValues = paddingValues,
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    }
                )
            },
            profileScreenContent = {

            },
            commentScreenContent = { newsItem->
                CommentsScreen(
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                    newsItem = newsItem
                )
            }
        )


    }

}

package com.alexp.vkfast.presentation.news

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexp.vkfast.R
import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.presentation.getApplicationComponent
import com.alexp.vkfast.ui.theme.DarkBlue

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (NewsItem) -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: NewsViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState by viewModel.screenState.collectAsState()

    NewsFeedScreenContent(
        screenState = screenState,
        paddingValues = paddingValues,
        onCommentClickListener = onCommentClickListener,
        viewModel = viewModel)
}


@Composable
private fun NewsFeedScreenContent(
    screenState: NewsFeedScreenState,
    paddingValues: PaddingValues,
    onCommentClickListener: (NewsItem) -> Unit,
    viewModel: NewsViewModel
)
{
    when (screenState) {
        is NewsFeedScreenState.Posts -> {
            NewsItems(
                posts = screenState.posts,
                viewModel = viewModel,
                paddingValues = paddingValues,
                onCommentClickListener = onCommentClickListener,
                nextDataIsLoading = screenState
            )
        }

        is NewsFeedScreenState.Initial -> {
        }

        is NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }

        is NewsFeedScreenState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {


                    Text(
                        text = screenState.message,
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                    IconButton(
                        onClick = { viewModel.loadNextRecommendations() },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            }
        }


        NewsFeedScreenState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error_loading_posts),
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun NewsItems(
    posts:List<NewsItem>,
    viewModel: NewsViewModel,
    paddingValues: PaddingValues,
    onCommentClickListener: (NewsItem) -> Unit,
    nextDataIsLoading:NewsFeedScreenState
)
{

    LazyColumn (
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 8.dp
        )
    ){
        items(items = posts, key = { it.newsId })
        { newsItem ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                        value->

                    val isDismissed = value in setOf(
                        SwipeToDismissBoxValue.EndToStart
                    )
                    if(isDismissed)
                    {
                        viewModel.remove(newsItem)
                    }
                    val isFavorite = value in setOf(
                        SwipeToDismissBoxValue.Settled
                    )
                    if(isFavorite)
                    {
                        viewModel.changeFavouriteStatus(newsItem)
                    }
                    return@rememberSwipeToDismissBoxState isDismissed||isFavorite
                }
            )
            SwipeToDismissBox(
                modifier = Modifier.animateItem(),
                state = dismissState,
                enableDismissFromStartToEnd = false,
                enableDismissFromEndToStart = true,
                backgroundContent =  {}){


                PostCard(
                    newsItem = newsItem,

                    onCommentItemClickListener = {
                        onCommentClickListener(newsItem)
                    },
                    onLikeItemClickListener = { _->
                        viewModel.changeLikeStatus(newsItem)
                    }

                )

            }
        }
    item {
        if(nextDataIsLoading is NewsFeedScreenState.Posts) {

            if (nextDataIsLoading.nextDataIsLoading)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    CircularProgressIndicator(color = DarkBlue)
                }
            else {
                SideEffect {
                    viewModel.loadNextRecommendations()

                }
            }
        }
    }
    }
}
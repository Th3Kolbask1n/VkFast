package com.alexp.vkfast.presentation.comments

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.alexp.vkfast.domain.entity.NewsItem
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.alexp.vkfast.R
import com.alexp.vkfast.domain.entity.PostComment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    onBackPressed: ()-> Unit,
    newsItem: NewsItem
) {
    val viewModel: CommentsViewModel = viewModel(
        factory = CommentsViewModelFactory(
            newsItem = newsItem,
            LocalContext.current.applicationContext as Application))
    val screenState = viewModel.screenState.collectAsState(CommentsScreenState.Initial)
    val currentState = screenState.value

    if (currentState is CommentsScreenState.Comments) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.comments_title))
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBackPressed()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),

                ) {
                items(items = currentState.comments, key = { it.commentId })
                { comment ->
                    CommentItem(comment = comment)
                }
            }

        }

}
}


@Composable
private fun CommentItem(comment: PostComment)
{
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(
            horizontal = 16.dp,
            vertical = 4.dp
        ))
    {
        AsyncImage(modifier = Modifier.size(48.dp)
            .clip(CircleShape),
            model = comment.userAvatarUrl,
            contentDescription = null)

        Spacer(modifier = Modifier.size(8.dp))
        Column {
            Text(
                text = comment.userName,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = comment.text,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = comment.postedAt,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp
            )
        }
    }

}

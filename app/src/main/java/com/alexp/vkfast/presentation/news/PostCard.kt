package com.alexp.vkfast.presentation.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alexp.vkfast.R
import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.entity.StatisticItem
import com.alexp.vkfast.domain.entity.StatisticType
import com.alexp.vkfast.ui.theme.DarkRed


@Composable

fun PostCard(
    modifier: Modifier = Modifier,
    newsItem: NewsItem,
    onLikeItemClickListener: (StatisticItem) -> Unit,
    onCommentItemClickListener: (StatisticItem) -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            PostHeader(newsItem)
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            Text(
                text = newsItem.textContent
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            AsyncImage(
                model = newsItem.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Statistics(
                statistics = newsItem.stats,
                onLikeItemClickListener = onLikeItemClickListener,
                onCommentItemClickListener = onCommentItemClickListener,

                isFavourite = newsItem.isLiked

                )
        }

    }
}

@Composable
private fun PostHeader(newsItem: NewsItem) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        AsyncImage(
            model = newsItem.communityImageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = newsItem.groupName,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = newsItem.publicationDate,
                color = MaterialTheme.colorScheme.onSecondary
            )

        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onLikeItemClickListener: (StatisticItem) -> Unit,
    onCommentItemClickListener: (StatisticItem) -> Unit,
    isFavourite : Boolean
) {
    Row {
        Row(
            modifier = Modifier.weight(1f)
        )
        {
            val viewItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                text = formatStatisticCount(viewItem.count),
               )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val statisticsItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(iconResId = R.drawable.ic_share,
                text = formatStatisticCount(statisticsItem.count),
               )
            val commentItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                text = formatStatisticCount(commentItem.count),
                onItemClickListener = {
                    onCommentItemClickListener(commentItem)
                })
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = if(isFavourite) R.drawable.ic_like_set else R.drawable.ic_like,
                text = formatStatisticCount(likesItem.count),
                onItemClickListener = {
                    onLikeItemClickListener(likesItem)
                }
            ,
                tint = if(isFavourite) DarkRed else MaterialTheme.colorScheme.onSecondary)


        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type }
        ?: throw IllegalStateException("StatisticItem of type $type not found")
}

@Composable
private fun IconWithText(
    iconResId: Int,
    text: String,
    onItemClickListener: (() -> Unit)? = null,
    tint: Color = MaterialTheme.colorScheme.onSecondary
) {
    val modifier = if(onItemClickListener==null)
    {
        Modifier
    }
    else
    {
        Modifier.clickable {
            onItemClickListener()
        }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}
private fun formatStatisticCount(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", (count / 1000))
    } else if (count > 1000) {
        String.format("%.1fK", (count / 1000f))
    } else {
        count.toString()
    }
}
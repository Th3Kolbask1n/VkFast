package com.alexp.vkfast.data.mapper

import android.util.Log
import com.alexp.vkfast.data.model.CommentsResponseDto
import com.alexp.vkfast.data.model.FavouritesPostsResponseDto
import com.alexp.vkfast.data.model.NewFeedResponseDto
import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.entity.PostComment
import com.alexp.vkfast.domain.entity.StatisticItem
import com.alexp.vkfast.domain.entity.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import kotlin.math.absoluteValue

class NewsFeedMapper @Inject constructor(){
    fun mapResponseToPosts(responseDto: NewFeedResponseDto): List<NewsItem> {
        val result = mutableListOf<NewsItem>()

        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups

        for (post in posts) {

            val group = groups.find {
                it.id == post.communityId.absoluteValue
            } ?: break
            val newsItem = NewsItem(
                newsId = post.id,
                groupId = post.communityId,
                groupName = group.name,
                publicationDate = mapTimestampToDate(post.date),
                communityImageUrl = group.imageUrl,
                textContent = post.text,
                imageUrl = post.attachments.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                stats = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticType.SHARES, post.repost.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count)
                ),
                isLiked = post.likes.userLikes>0,
                isFavourite = post.userFavourite

            )
            result.add(newsItem)
        }
        return result
    }
    fun mapResponseToComments(response:CommentsResponseDto):List<PostComment>{
        val result = mutableListOf<PostComment>()
        val comments = response.conent.comments
        val profiles = response.conent.profiles

        for(comment in comments)
        {
            if(comment.text.isBlank()) continue
            val author = profiles.firstOrNull{it.id == comment.authorId}?: continue
            val postComment = PostComment(
                commentId = comment.id,
                userName = "${author.firstName} ${author.lastName}",
                userAvatarUrl = author.avatarUrl,
                text = comment.text,
                postedAt = mapTimestampToDate(comment.date)

            )

            result.add(postComment)
        }

        return result
    }
    fun mapFavouriteResponseToPosts(responseDto: FavouritesPostsResponseDto): List<NewsItem> {
        val result = mutableListOf<NewsItem>()

        val posts = responseDto.favPostsContent.posts
        val groups = responseDto.favPostsContent.groups

        for (postResponse in posts) {

            Log.d("WHIE","${groups}")
            val post = postResponse.favPostsList
            val group = groups.find {
                it.id == post.communityId.absoluteValue
            } ?: break

            val newsItem = NewsItem(
                newsId = post.id,
                groupId = post.communityId,
                groupName = group.name,
                publicationDate = mapTimestampToDate(post.date),
                communityImageUrl = group.imageUrl,
                textContent = post.text,
                imageUrl = post.attachments.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                stats = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticType.SHARES, post.repost.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count)
                ),
                isLiked = post.likes.userLikes>0,
                isFavourite = post.userFavourite

            )
            result.add(newsItem)
        }
        return result
    }


    private fun mapTimestampToDate(timestamp: Long):String
    {
        val date = Date(timestamp*1000)

        return SimpleDateFormat("d MMMM yyyy, hh:mm",java.util.Locale.getDefault()).format(date)
    }
}
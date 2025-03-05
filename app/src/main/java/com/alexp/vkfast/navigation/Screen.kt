package com.alexp.vkfast.navigation

import android.net.Uri
import com.alexp.vkfast.domain.entity.NewsItem
import com.google.gson.Gson

sealed class Screen(
    val route:String
)
{

    object NewsFeed:Screen(ROUTE_NEWS_FEED)
    object Favourite: Screen(ROUTE_FAVOURITE)
    object Profile:Screen(ROUTE_PROFILE)

    object Home:Screen(ROUTE_HOME)
    object Comments:Screen(ROUTE_COMMENTS){

        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(newsItem: NewsItem):String
        {
            val newsItemJson = Gson().toJson(newsItem)
            return "$ROUTE_FOR_ARGS/${newsItemJson.encode()}"
        }
    }
    object Auth : Screen("auth")



    companion object
    {

        const val KEY_FEED_POST_ID = "feed_post_id"
        const val KEY_CONTENT_TEXT = "feed_post_id"
        const val KEY_FEEDPOST = "feed_post"


        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"

        const val ROUTE_COMMENTS ="comments/{$KEY_FEEDPOST}"
        const val ROUTE_HOME = "home"
    }

    fun String.encode():String{
        return Uri.encode(this)
    }
}
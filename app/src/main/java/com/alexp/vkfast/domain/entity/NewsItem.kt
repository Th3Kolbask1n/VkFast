package com.alexp.vkfast.domain.entity

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize


@Parcelize
data class NewsItem (
    val newsId: Long,
    val groupId:Long,
    val groupName: String,
    val publicationDate: String,
    val communityImageUrl : String,
    val textContent:String,
    val imageUrl:String?,
    val stats:List<StatisticItem>,
    val isLiked:Boolean

):Parcelable

{
    companion object{
        
        val NavigationType:NavType<NewsItem> = object : NavType<NewsItem>(false) {
            override fun get(bundle: Bundle, key: String): NewsItem? {
                return  bundle.getParcelable(key)
            }

            override fun parseValue(value: String): NewsItem {
                return  Gson().fromJson(value, NewsItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: NewsItem) {
                bundle.putParcelable(key,value)
            }

        }
    }
}


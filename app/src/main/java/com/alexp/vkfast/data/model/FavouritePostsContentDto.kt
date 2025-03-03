package com.alexp.vkfast.data.model

import com.google.gson.annotations.SerializedName

data class FavouritePostsContentDto(
    @SerializedName("items") val posts:List<FavouritesCountDto>,
    @SerializedName("groups") val groups: List<GroupDto>,

    @SerializedName("next_from") val nextFrom: String?

)

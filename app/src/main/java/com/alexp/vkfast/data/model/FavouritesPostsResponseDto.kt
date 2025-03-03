package com.alexp.vkfast.data.model

import com.google.gson.annotations.SerializedName

data class FavouritesPostsResponseDto(
    @SerializedName("response") val favPostsContent: FavouritePostsContentDto,
    @SerializedName("next_from") val nextFrom: String?

)

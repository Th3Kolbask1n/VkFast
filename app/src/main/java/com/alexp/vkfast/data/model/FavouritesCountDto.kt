package com.alexp.vkfast.data.model

import com.google.gson.annotations.SerializedName

data class FavouritesCountDto(

    @SerializedName("post") val favPostsList :FavouritePostDto
)
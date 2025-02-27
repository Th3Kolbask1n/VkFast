package com.alexp.vkfast.data.model

import com.google.gson.annotations.SerializedName

data class NewFeedResponseDto(
    @SerializedName("response") val newsFeedContent: NewsFeedContentDto

    )

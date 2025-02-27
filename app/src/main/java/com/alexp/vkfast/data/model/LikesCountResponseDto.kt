package com.alexp.vkfast.data.model

import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto(
    @SerializedName("response") val likes :LikesCountDto

)

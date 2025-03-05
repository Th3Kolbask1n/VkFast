package com.alexp.vkfast.data.model

import com.google.gson.annotations.SerializedName

data class UserInfoResponseDto(
    @SerializedName("response") val userInfoContent: UserInfoDto

    )

package com.alexp.vkfast.data.model

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("sizes") val photoUrls:List<PhotoUrlDto>

)

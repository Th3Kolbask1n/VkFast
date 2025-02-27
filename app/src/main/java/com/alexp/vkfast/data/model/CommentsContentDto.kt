package com.alexp.vkfast.data.model

import com.google.gson.annotations.SerializedName
import org.w3c.dom.Comment

data class CommentsContentDto
    (
    @SerializedName("items") val comments:List<CommentDto>,
    @SerializedName("profiles") val profiles:List<ProfileDto>,

    )
package com.alexp.vkfast.data.model

import com.google.gson.annotations.SerializedName

data class FavouritePostDto(
    @SerializedName("id") val id:Long,
    @SerializedName("from_id") val communityId:Long,
    @SerializedName("text") val text:String,
    @SerializedName("date") val date:Long,
    @SerializedName("likes") val likes:LikesDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("views") val views: ViewsDto,
    @SerializedName("reposts") val repost: RepostsDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>,
    @SerializedName("is_favorite") val userFavourite: Boolean


    )

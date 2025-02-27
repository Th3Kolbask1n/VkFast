package com.alexp.vkfast.domain.entity

data class PostComment(
    val commentId:Long,
    val userName:String,
    val userAvatarUrl:String,
    val text:String,
    val postedAt: String
)
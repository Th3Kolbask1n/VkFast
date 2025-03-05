package com.alexp.vkfast.domain.entity

data class UserInfo(
     val id: Long,
     val homeTown: String?,
     val status: String?,
     val photo200: String,
     val isServiceAccount: Boolean,
     val birthDate: String?,
     val verificationStatus: String,
     val firstName: String,
     val lastName: String,
     val bdateVisibility: Int,
     val city: Map<String, Any>?,
     val phone: String,
     val relation: Int,
     val sex: Int
)
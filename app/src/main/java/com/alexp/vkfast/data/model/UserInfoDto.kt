package com.alexp.vkfast.data.model

import com.google.gson.annotations.SerializedName

data class UserInfoDto(
    @SerializedName("id") val id: Long,
    @SerializedName("home_town") val homeTown: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("photo_200") val photo200: String,
    @SerializedName("is_service_account") val isServiceAccount: Boolean,
    @SerializedName("bdate") val birthDate: String?,
    @SerializedName("verification_status") val verificationStatus: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("bdate_visibility") val bdateVisibility: Int,
    @SerializedName("city") val city: Map<String, Any>?,
    @SerializedName("phone") val phone: String,
    @SerializedName("relation") val relation: Int,
    @SerializedName("sex") val sex: Int
)
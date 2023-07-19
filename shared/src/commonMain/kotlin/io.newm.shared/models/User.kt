package io.newm.shared.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val createdAt: String,
    val oauthType: String? = null,
    val oauthId: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val nickname: String? = null,
    val pictureUrl: String? = null,
    val bannerUrl: String? = null,
    val websiteUrl: String? = null,
    val twitterUrl: String? = null,
    val instagramUrl: String? = null,
    val location: String? = null,
    val role: String? = null,
    val genre: String? = null,
    val biography: String? = null,
    val walletAddress: String? = null,
    val email: String? = null,
    val companyName: String? = null,
    val companyLogoUrl: String? = null,
    val companyIpRights: String? = null,
    val verificationStatus: String? = null,
    val currentPassword: String? = null,
    val newPassword: String? = null,
    val confirmPassword: String? = null
)

@Serializable
internal data class UserCount(
    val count: Int
)
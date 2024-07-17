package io.newm.shared.internal.api.models

import kotlinx.serialization.Serializable

@Serializable
internal data class LogInUser(
    val email: String,
    val password: String
)

@Serializable
internal data class GoogleSignInRequest(val accessToken: String)

@Serializable
internal data class AppleSignInRequest(val idToken: String)

@Serializable
internal data class FacebookSignInRequest(val accessToken: String)

@Serializable
internal data class LinkedInSignInRequest(val accessToken: String)

@Serializable
internal data class NewUser(
    val firstName: String? = null,
    val lastName: String? = null,
    val pictureUrl: String? = null,
    val email: String,
    val newPassword: String,
    val confirmPassword: String,
    val authCode: String
)

@Serializable
internal data class UserProfileUpdateRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val nickname: String? = null,
    val pictureUrl: String? = null,
    val bannerUrl: String? = null,
    val websiteUrl: String? = null,
    val twitterUrl: String? = null,
    val instagramUrl: String? = null,
    val spotifyProfile: String? = null,
    val soundCloudProfile: String? = null,
    val appleMusicProfile: String? = null,
    val location: String? = null,
    val role: String? = null,
    val genre: String? = null,
    val biography: String? = null,
    val companyName: String? = null,
    val companyLogoUrl: String? = null,
    val companyIpRights: Boolean? = null,
    val dspPlanSubscribed: Boolean? = null,
    val walletAddress: String? = null,
    val email: String? = null,
    val newPassword: String? = null,
    val confirmPassword: String? = null,
    val currentPassword: String? = null,
    val authCode: String? = null
)

@Serializable
internal data class ResetPasswordRequest(
    val email: String,
    val newPassword: String,
    val confirmPassword: String,
    val authCode: String
)
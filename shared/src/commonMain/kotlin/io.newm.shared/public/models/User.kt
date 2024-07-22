package io.newm.shared.public.models

import kotlinx.serialization.Serializable

/**
 * A data class representing a user, annotated with `@Serializable` for serialization purposes.
 *
 * This class encapsulates various properties of a user, including personal details,
 * social media links, location, role, and account information. Optional properties are nullable.
 *
 * @property id Unique identifier of the user.
 * @property createdAt Timestamp of when the user account was created.
 * @property oauthType Type of OAuth used for authentication, if applicable.
 * @property oauthId OAuth identifier, if OAuth is used for authentication.
 * @property firstName First name of the user.
 * @property lastName Last name of the user.
 * @property nickname Nickname of the user.
 * @property pictureUrl URL of the user's profile picture.
 * @property bannerUrl URL of the user's profile banner.
 * @property websiteUrl URL of the user's personal or professional website.
 * @property twitterUrl URL of the user's Twitter profile.
 * @property instagramUrl URL of the user's Instagram profile.
 * @property location Geographical location of the user.
 * @property role Role or title of the user.
 * @property genre Favorite or representative genre of the user.
 * @property biography Short biography of the user.
 * @property walletAddress Blockchain wallet address of the user.
 * @property email Email address of the user.
 * @property companyName Name of the company associated with the user.
 * @property companyLogoUrl URL of the company's logo.
 * @property companyIpRights Information about intellectual property rights held by the company.
 * @property verificationStatus Status of the user's account verification.
 * @property currentPassword Current password of the user (used for account updates).
 * @property newPassword New password for the user (used for account updates).
 * @property confirmPassword Confirmation of the new password (used for account updates).
 */
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

fun User.fullName(): String {
    return "${firstName.orEmpty().trim()} ${lastName.orEmpty().trim()}".trim()
}

fun User.canEditName(): Boolean {
    return verificationStatus == "Unverified"
}
package io.newm.shared.internal.repositories.models

sealed interface OAuthData {
    data class Google(val idToken: String) : OAuthData
    data class Facebook(val accessToken: String) : OAuthData
    data class LinkedIn(val accessToken: String) : OAuthData
    data class Apple(val idToken: String) : OAuthData
}

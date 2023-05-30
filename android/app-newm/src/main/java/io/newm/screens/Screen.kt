package io.newm.screens

sealed class Screen(val route: String) {
    //High Navigation Roots
    object HomeRoot : Screen("home-root")
    object HomeLanding : Screen("home-landing")

    object LibraryRoot : Screen("library-root")

    object LibraryLanding : Screen("library-landing")

    object SearchRoot : Screen("search-root")

    object SearchLanding : Screen("search-landing")

    object StarsRoot : Screen("stars-root")
    object StarsLanding : Screen("stars-landing")

    object WalletRoot : Screen("wallet-root")
    object WalletLanding : Screen("wallet-landing")

    //Single Screens
    object LoginLandingScreen : Screen("login-landing")
    object LoginScreen : Screen("login")
    object Signup : Screen("signup")
    object VerificationCode : Screen("verification")
    object WhatShouldWeCallYou : Screen("what-should-we-call-you")
    object NowPlayingScreen : Screen("now-playing")
    object Profile : Screen("profile")
}
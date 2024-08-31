package io.newm.shared.public.analytics.events

import io.newm.shared.public.analytics.ScreenEvents


object AppScreens {
    //General Screens
    object LoadingScreen: ScreenEvents { override val name = "Loading" }
    object ErrorScreen: ScreenEvents{ override val name = "Error" }

    //Main Screens
    object AccountOptionsScreen: ScreenEvents{ override val name = "Account Options" }
    object AccountScreen: ScreenEvents{ override val name = "Account" }
    object ConnectWalletScannerScreen: ScreenEvents{ override val name = "Connect Wallet Scanner" }
    object EditProfileScreen: ScreenEvents{ override val name = "Edit Profile" }
    object EmailVerificationScreen: ScreenEvents{ override val name = "Email Verification" }
    object ForceUpdateScreen: ScreenEvents{ override val name = "Force App Update" }
    object LogoutConfirmationDialogScreen: ScreenEvents{ override val name = "Logout Confirmation Dialog" }
    object MusicPlayerScreen : ScreenEvents { override val name = "Music Player" }
    object NFTLibraryEmptyWalletScreen: ScreenEvents{ override val name = "NFT Library Empty Wallet" }
    object NFTLibraryFilterScreen: ScreenEvents{ override val name = "NFT Library Filter" }
    object NFTLibraryLinkWalletScreen: ScreenEvents{ override val name = "NFT Library Link Wallet" }
    object NFTLibraryScreen: ScreenEvents{ override val name = "NFT Library" }
    object NewPasswordScreen: ScreenEvents{ override val name = "Reset Password Set New Password" }
    object PrivacyPolicyScreen: ScreenEvents{ override val name = "Privacy Policy"}
    object RecordStoreScreen: ScreenEvents{ override val name = "Record Store" }
    object ResetPasswordEnterCodeScreen: ScreenEvents{ override val name = "Reset Password Enter Code" }
    object ResetPasswordEnterEmailScreen: ScreenEvents{ override val name = "Reset Password Enter Email" }
    object TermsAndConditionsScreen: ScreenEvents{ override val name = "Terms And Conditions"}
    object WalletInstructionsScreen: ScreenEvents{ override val name = "Wallet Instructions" }
    object WelcomeScreen: ScreenEvents{ override val name = "Welcome" }
}
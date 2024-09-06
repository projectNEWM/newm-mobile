package io.newm.shared.public.analytics.events

import io.newm.shared.public.analytics.ScreenEvents


object AppScreens {
    //General Screens
    object LoadingScreen : ScreenEvents {
        override val name = "Loading"
    }

    object ErrorScreen : ScreenEvents {
        override val name = "Error"
    }

    //Main Screens
    object AccountOptionsScreen : ScreenEvents {
        override val name = "Account Options"
    }

    object AccountScreen : ScreenEvents {
        override val name = "Account"
    }

    object ConnectWalletScannerScreen : ScreenEvents {
        override val name = "Connect Wallet Scanner"
    }

    object EditProfileScreen : ScreenEvents {
        override val name = "Edit Profile"
    }

    object EmailVerificationScreen : ScreenEvents {
        override val name = "Email Verification"
        const val CONTINUE_BUTTON: String = "Continue"
    }

    object ForceUpdateScreen : ScreenEvents {
        override val name = "Force App Update"
        const val UPDATE_BUTTON: String = "Update Now"

    }

    object LogoutConfirmationDialogScreen : ScreenEvents {
        override val name = "Logout Confirmation Dialog"
    }

    object MusicPlayerScreen : ScreenEvents {
        override val name = "Music Player"
    }

    object NFTLibraryEmptyWalletScreen : ScreenEvents {
        override val name = "NFT Library Empty Wallet"
    }

    object NFTLibraryFilterScreen : ScreenEvents {
        override val name = "NFT Library Filter"
    }

    object NFTLibraryLinkWalletScreen : ScreenEvents {
        override val name = "NFT Library Link Wallet"
    }

    object NFTLibraryScreen : ScreenEvents {
        override val name = "NFT Library"
    }

    object NewPasswordScreen : ScreenEvents {
        override val name = "Reset Password Set New Password"
        const val CONFIRM_BUTTON: String = "Confirm"
    }

    object PrivacyPolicyScreen : ScreenEvents {
        override val name = "Privacy Policy"
    }

    object RecordStoreScreen : ScreenEvents {
        override val name = "Record Store"
    }

    object ResetPasswordEnterCodeScreen : ScreenEvents {
        override val name = "Reset Password Enter Code"
        const val CONTINUE_BUTTON: String = "Continue"
    }

    object ResetPasswordEnterEmailScreen : ScreenEvents {
        override val name = "Reset Password Enter Email"
        const val CONTINUE_BUTTON: String = "Continue"
    }

    object TermsAndConditionsScreen : ScreenEvents {
        override val name = "Terms And Conditions"
    }

    object WalletInstructionsScreen : ScreenEvents {
        override val name = "Wallet Instructions"
    }

    object WelcomeScreen : ScreenEvents {
        override val name = "Welcome"
        const val CREATE_ACCOUNT_BUTTON: String = "Create account"
        const val LOGIN_WITH_EMAIL_BUTTON: String = "Login with Email"
        const val LOGIN_WITH_GOOGLE_BUTTON: String = "Login with Google"
    }

    object LogInWithEmailScreen : ScreenEvents {
        override val name = "Login With Email"
        const val FORGOT_PASSWORD_BUTTON: String = "Forgot your password?"
        const val LOGIN_BUTTON: String = "Login"
    }

    object CreateAccountScreen : ScreenEvents {
        override val name = "Create Account"
        const val NEXT_BUTTON: String = "Next"
    }

}
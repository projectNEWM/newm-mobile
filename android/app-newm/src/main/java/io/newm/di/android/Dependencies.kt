package io.newm.di.android

import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import io.newm.Logout
import io.newm.RestartApp
import io.newm.feature.login.screen.LoginViewModel
import io.newm.feature.login.screen.authproviders.google.GoogleSignInLauncher
import io.newm.feature.login.screen.authproviders.google.GoogleSignInLauncherImpl
import io.newm.feature.login.screen.createaccount.CreateAccountScreenPresenter
import io.newm.feature.login.screen.login.LoginScreenPresenter
import io.newm.feature.login.screen.resetpassword.ResetPasswordScreenPresenter
import io.newm.feature.login.screen.welcome.WelcomeScreenPresenter
import io.newm.feature.musicplayer.repository.MockMusicRepository
import io.newm.feature.musicplayer.repository.MusicRepository
import io.newm.feature.musicplayer.viewmodel.MusicPlayerViewModel
import io.newm.screens.home.categories.MusicalCategoriesViewModel
import io.newm.screens.library.NFTLibraryViewModel
import io.newm.screens.account.UserAccountViewModel
import io.newm.screens.profile.edit.ProfileEditViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object Constants { // TODO move to build config
    const val googleAuthServerClientId =
        "489785482974-d5g8ggup9c9e1uod25lvbop0hpd4thgr.apps.googleusercontent.com"
}

val viewModule = module {
    viewModelOf(::MusicalCategoriesViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::UserAccountViewModel)
    viewModelOf(::ProfileEditViewModel)
    viewModelOf(::NFTLibraryViewModel)
    viewModel { params -> MusicPlayerViewModel(params.get(), params.get(), get(), get()) }

    factory { params -> CreateAccountScreenPresenter(params.get(), get()) }
    factory { params -> LoginScreenPresenter(params.get(), get()) }
    factory { params -> ResetPasswordScreenPresenter(params.get(), get(), get()) }
    single<GoogleSignInLauncher> {
        GoogleSignInLauncherImpl(
            GoogleSignIn.getClient(
                androidContext(),
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(Constants.googleAuthServerClientId)
                    .requestScopes(Scope(Scopes.EMAIL), Scope(Scopes.PROFILE))
                    .requestEmail()
                    .build()
            )
        )
    }
    factory { params ->
        WelcomeScreenPresenter(
            navigator = params.get(),
            googleSignInLauncher = get(),
            loginUseCase = get(),
            activityResultContract = ActivityResultContracts.StartActivityForResult()
        )
    }
    single<MusicRepository> { MockMusicRepository(androidContext()) }
}

val androidModules = module {
    single { Logout(get(), get(), get()) }
    single { RestartApp(get()) }
}

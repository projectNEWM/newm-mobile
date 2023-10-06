package io.newm.di.android

import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import io.newm.Logout
import io.newm.RestartApp
import io.newm.screens.home.categories.MusicalCategoriesViewModel
import io.newm.feature.login.screen.createaccount.CreateAccountViewModel
import io.newm.screens.profile.ProfileViewModel
import io.newm.feature.login.screen.LoginViewModel
import io.newm.screens.library.NFTLibraryViewModel
import io.newm.feature.now.playing.MusicPlayerViewModel
import io.newm.feature.login.screen.createaccount.CreateAccountScreenPresenter
import io.newm.feature.login.screen.authproviders.google.GoogleSignInLauncher
import io.newm.feature.login.screen.authproviders.google.GoogleSignInLauncherImpl
import io.newm.feature.login.screen.welcome.WelcomeScreenPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object Constants { // TODO move to build config
    const val googleAuthServerClientId = "489785482974-d5g8ggup9c9e1uod25lvbop0hpd4thgr.apps.googleusercontent.com"
}

val viewModule = module {
    viewModelOf(::MusicalCategoriesViewModel)
    viewModelOf(::CreateAccountViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::NFTLibraryViewModel)
    viewModelOf(::MusicPlayerViewModel)
    factory { params -> CreateAccountScreenPresenter(params.get(), get()) }
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
            repository = get(),
            activityResultContract = ActivityResultContracts.StartActivityForResult()
        )
    }
}

val androidModules = module {
    single { Logout(get(), get()) }
    single { RestartApp(get()) }
}

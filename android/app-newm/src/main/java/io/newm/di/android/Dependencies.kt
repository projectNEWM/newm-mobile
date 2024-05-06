@file:OptIn(FlowPreview::class)

package io.newm.di.android

import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import io.newm.Logout
import io.newm.RestartApp
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
import io.newm.screens.profile.edit.ProfileEditViewModel
import io.newm.feature.login.screen.authproviders.RecaptchaClientProvider
import io.newm.screens.account.UserAccountPresenter
import io.newm.screens.library.NFTLibraryPresenter
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.config.NewmSharedBuildConfigImpl
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModule = module {
    viewModelOf(::MusicalCategoriesViewModel)
    viewModelOf(::ProfileEditViewModel)
    viewModel { params -> MusicPlayerViewModel(params.get(), params.get(), get(), get()) }
    single { RecaptchaClientProvider()  }

    factory { params -> CreateAccountScreenPresenter(params.get(), get(), get()) }
    factory { params -> LoginScreenPresenter(params.get(), get(), get()) }
    factory { params -> ResetPasswordScreenPresenter(params.get(), get(), get(), get()) }
    single<NewmSharedBuildConfig> { NewmSharedBuildConfigImpl }
    single<GoogleSignInLauncher> {
        val sharedBuildConfig = get<NewmSharedBuildConfig>()

        GoogleSignInLauncherImpl(
            GoogleSignIn.getClient(
                androidContext(),
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(sharedBuildConfig.googleAuthClientId)
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
            recaptchaClientProvider = get(),
            loginUseCase = get(),
            activityResultContract = ActivityResultContracts.StartActivityForResult()
        )
    }
    factory { params ->
        UserAccountPresenter(
            params.get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    factory { params ->
        NFTLibraryPresenter(
            params.get(),
            get(),
            get()
        )
    }
    single<MusicRepository> { MockMusicRepository(androidContext()) }
}

val androidModules = module {
    single { Logout(get(), get(), get()) }
    single { RestartApp(get()) }
}

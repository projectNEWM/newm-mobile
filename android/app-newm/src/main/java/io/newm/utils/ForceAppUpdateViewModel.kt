package io.newm.utils

import androidx.lifecycle.ViewModel
import com.google.android.recaptcha.RecaptchaAction
import io.newm.feature.login.screen.authproviders.RecaptchaClientProvider
import io.newm.shared.NewmAppLogger
import io.newm.shared.public.usecases.ForceAppUpdateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext


class ForceAppUpdateViewModel(
    private val forceAppUpdateUseCase: ForceAppUpdateUseCase,
    private val recaptchaClientProvider: RecaptchaClientProvider,
) : ViewModel() {
    private val _updateRequiredState = MutableStateFlow(false)
    val updateRequiredState: StateFlow<Boolean> get() = _updateRequiredState

    suspend fun checkForUpdates(currentVersion: String) = withContext(Dispatchers.IO) {
        try {
            val recaptchaClient = recaptchaClientProvider.get()
            recaptchaClient.execute(RecaptchaAction.custom("mobile_config"))
                .onSuccess { token ->
                    val updateRequired = forceAppUpdateUseCase.isAndroidUpdateRequired(currentVersion, token)
                    _updateRequiredState.value = updateRequired
                }
                .onFailure {
                    _updateRequiredState.value = false
                }
        } catch (e: Exception) {
            _updateRequiredState.value = false
        }
    }
}
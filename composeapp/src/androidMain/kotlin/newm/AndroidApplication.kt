package newm

import android.app.Application
import com.google.android.recaptcha.Recaptcha
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import newm.inject.AndroidApplicationComponent
import newm.inject.ApplicationComponentProvider
import newm.inject.create

class AndroidApplication : Application(), ApplicationComponentProvider {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override val component by lazy(LazyThreadSafetyMode.NONE) {
        AndroidApplicationComponent::class.create(this)
    }

    override fun onCreate() {
        super.onCreate()
        initializeRecaptchaClient()
    }

    private fun initializeRecaptchaClient() {
        val config = component.config
        val provider = component.recaptchaClientProvider

        coroutineScope.launch {
            Recaptcha.getClient(this@AndroidApplication, config.recaptchaSiteKey)
                .onSuccess { client ->
                    provider.setRecaptchaClient(client)
                }
                .onFailure { e ->
                    e.printStackTrace()
                }
        }
    }
}
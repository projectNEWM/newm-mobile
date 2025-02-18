package newm

import android.app.Application
import inject.AndroidApplicationComponent
import inject.ApplicationComponentProvider
import inject.create

class AndroidApplication : Application(), ApplicationComponentProvider {
    override val component by lazy(LazyThreadSafetyMode.NONE) {
        AndroidApplicationComponent::class.create(this)
    }
}

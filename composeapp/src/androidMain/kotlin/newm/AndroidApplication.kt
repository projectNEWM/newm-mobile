package newm

import android.app.Application
import newm.inject.AndroidApplicationComponent
import newm.inject.ApplicationComponentProvider
import newm.inject.create

class AndroidApplication : Application(), ApplicationComponentProvider {
    override val component by lazy(LazyThreadSafetyMode.NONE) {
        AndroidApplicationComponent::class.create(this)
    }
}

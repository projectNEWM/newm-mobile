package io.projectnewm

import android.app.Application
import com.airbnb.android.showkase.annotation.ShowkaseRoot
import com.airbnb.android.showkase.annotation.ShowkaseRootModule
import io.projectnewm.di.cacheModule
import io.projectnewm.di.networkModule
import io.projectnewm.di.viewmodels.viewModule
import io.projectnewm.shared.commonModule
import io.projectnewm.shared.initKoin
import org.koin.android.ext.koin.androidContext

@ShowkaseRoot
class NewmApplication : Application(), ShowkaseRootModule {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@NewmApplication)
            modules(
                commonModule,
                networkModule,
                cacheModule,
                viewModule
            )
        }
    }
}

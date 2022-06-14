package io.projectnewm

import android.app.Application
import com.airbnb.android.showkase.annotation.ShowkaseRoot
import com.airbnb.android.showkase.annotation.ShowkaseRootModule
import io.projectnewm.di.cacheModule
import io.projectnewm.di.networkModule
import io.projectnewm.di.usecase.loginModule
import io.projectnewm.di.viewmodels.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ShowkaseRoot
class NewmApplication : Application(), ShowkaseRootModule {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NewmApplication)
            modules(
                networkModule,
                cacheModule,
                loginModule,
                viewModule
            )
        }
    }
}

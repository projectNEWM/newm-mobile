package io.projectnewm

import android.app.Application
import com.airbnb.android.showkase.annotation.ShowkaseRoot
import com.airbnb.android.showkase.annotation.ShowkaseRootModule
import io.projectnewm.di.cacheModule
import io.projectnewm.di.networkModule
import io.projectnewm.di.usecase.login
import io.projectnewm.di.viewmodels.viewModule
import io.projectnewm.screens.home.categories.MusicalCategoriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module

@ShowkaseRoot
class NewmApplication : Application(), ShowkaseRootModule {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NewmApplication)
            modules(
                networkModule,
                cacheModule,
                login,
                viewModule
            )
        }
    }
}

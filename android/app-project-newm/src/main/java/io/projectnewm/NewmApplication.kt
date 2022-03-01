package io.projectnewm

import android.app.Application
import com.airbnb.android.showkase.annotation.ShowkaseRoot
import com.airbnb.android.showkase.annotation.ShowkaseRootModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
@ShowkaseRoot
class NewmApplication : Application(), ShowkaseRootModule
package newm.inject

import android.content.Context

interface ApplicationComponentProvider {
    val component: AndroidApplicationComponent
}

val Context.applicationComponent get() = (applicationContext as ApplicationComponentProvider).component

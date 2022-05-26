package io.projectnewm

import android.app.Application
import android.content.Context
import com.karumi.shot.ShotTestRunner

class NewmAndroidJUnitRunner:  ShotTestRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, NewmApplication::class.java.name, context)
    }
}
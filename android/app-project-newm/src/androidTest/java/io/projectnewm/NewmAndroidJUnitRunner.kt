package io.projectnewm

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.karumi.shot.ShotTestRunner
import dagger.hilt.android.testing.HiltTestApplication

class NewmAndroidJUnitRunner:  ShotTestRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
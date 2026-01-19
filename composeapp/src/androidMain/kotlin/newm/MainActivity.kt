package newm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import newm.inject.InjectAndroidActivityComponent
import newm.inject.applicationComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val activityComponent = InjectAndroidActivityComponent(applicationComponent)

        setContent {
            App(
                circuit = activityComponent.circuit,
                config = activityComponent.config,
                onRootPop = { onBackPressedDispatcher.onBackPressed() }
            )
        }
    }
}
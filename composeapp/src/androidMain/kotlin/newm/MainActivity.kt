package newm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import inject.InjectAndroidActivityComponent
import inject.applicationComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val activityComponent = InjectAndroidActivityComponent(applicationComponent)

        setContent {
            App(
                circuit = activityComponent.circuit,
                onRootPop = { onBackPressedDispatcher.onBackPressed() }
            )
        }
    }
}
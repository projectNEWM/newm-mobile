package com.projectnewm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.projectnewm.compose.NewmApp
import com.projectnewm.ui.theme.NewmMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewmMobileTheme {
                NewmApp()
            }
        }
    }
}
package com.projectnewm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.projectnewm.compose.NEWMApp
import com.projectnewm.ui.theme.NewmmobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewmmobileTheme {
                NEWMApp()
            }
        }
    }
}
package com.junbin.compose_toss

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.junbin.compose_toss.view.MainScreen
import com.junbin.compose_toss.view.ui.theme.Compose_tossTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose_tossTheme {
                MainScreen()
            }
        }
    }
}


//MainBackground -> MainWidget -> MainModule
//MainWidget -> TopBarModule, BottomNavigationModule, MainModule

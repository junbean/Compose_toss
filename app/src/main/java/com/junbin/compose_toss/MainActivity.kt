package com.junbin.compose_toss

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.junbin.compose_toss.view.MainScreen
import com.junbin.compose_toss.view.ui.theme.Compose_tossTheme
import com.junbin.compose_toss.viewmodel.StockViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stockViewModel = ViewModelProvider(this)[StockViewModel::class]
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

package com.junbin.compose_toss.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.junbin.compose_toss.view.components.TopBarModule

@Composable
fun StockPage() {
    Scaffold(
        topBar = {
            TopBarModule()
        }
    ) { innerPadding ->
        CertificateModule(Modifier.padding(innerPadding))
    }
}


@Composable
fun CertificateModule(modifier: Modifier = Modifier) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("asdf")

        }
    }
}
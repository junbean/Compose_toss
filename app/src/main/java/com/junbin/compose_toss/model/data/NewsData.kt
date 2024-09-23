package com.junbin.compose_toss.model.data

import androidx.compose.ui.graphics.ImageBitmap

data class NewsData(
    val stock : List<StockData>,
    val newsImg: Int,
    val newsContent : String,
    val news: String,
    val hour : Int
)

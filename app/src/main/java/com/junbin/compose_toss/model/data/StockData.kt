package com.junbin.compose_toss.model.data

import android.os.Parcelable

data class StockData(
    val name: String,
    val price: Int,
    val change: Double,
    val unit: String,
    val stockImage: Int
)

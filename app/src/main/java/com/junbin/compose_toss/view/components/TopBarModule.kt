@file:OptIn(ExperimentalAnimationApi::class)

package com.junbin.compose_toss.view.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junbin.compose_toss.model.data.KoreaStockData
import com.junbin.compose_toss.view.ui.theme.DecreaseTextColor
import com.junbin.compose_toss.view.ui.theme.DefaultTextColor
import com.junbin.compose_toss.view.ui.theme.IncreaseTextColor
import kotlinx.coroutines.delay

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarModule() {
    val timer: Long = 5000
    val koreaStockList = listOf(
        KoreaStockData("코스피", 2574.39, -0.03),
        KoreaStockData("코스닥", 736.36, +0.4)
    )

    TopAppBar(
        modifier = Modifier,
        title = {
            StockTextAnimated(itemList = koreaStockList, timer = timer)
        },
        actions = {
            IconButton(
                onClick = { /* 돋보기 클릭 시 동작 */ }
            ) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
            IconButton(
                onClick = { /* 햄버거 메뉴 클릭 시 동작 */ }
            ) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        }
    )
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun StockTextAnimated(
    itemList: List<KoreaStockData>,
    timer: Long
) {
    var currentStockIndex by remember { mutableIntStateOf(0) }
    val currentStock = itemList[currentStockIndex]
    var isVisible by remember { mutableStateOf(true) }

    // 3초마다 애니메이션을 트리거
    LaunchedEffect(Unit) {
        while (true) {
            delay(timer)
            isVisible = false // 먼저 기존 텍스트를 비활성화
            delay(300) // 300ms 뒤에 다음 텍스트 활성화
            currentStockIndex = (currentStockIndex + 1) % itemList.size
            isVisible = true
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
        ) {
            // 텍스트 색상 변경
            val textColor = if (currentStock.change > 0) IncreaseTextColor else DecreaseTextColor

            StockTextRow(16.sp, textColor, currentStock)
        }
    }
}

@Composable
fun StockTextRow(fontSize: TextUnit, textColor: Color, stockData: KoreaStockData,) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stockData.name,
            color = DefaultTextColor,
            fontSize = fontSize
        )
        Spacer(
            modifier = Modifier.width(6.dp)
        )
        Text(
            text = "${stockData.price}",
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontSize = fontSize
        )
        Spacer(
            modifier = Modifier.width(6.dp)
        )
        Text(
            text = if(stockData.change >= 0) "+${stockData.change}%" else "${stockData.change}%",
            color = textColor,
            fontSize = fontSize
        )
    }
}

/*

LaunchedEffect(Unit) {
    while (true) {
        delay(timer) // 3초 지연
        currentStockIndex = (currentStockIndex + 1) % itemList.size
    }
}

AnimatedContent(
    targetState = currentStock,
    transitionSpec = {
        if (targetState.name > initialState.name) {
            slideInVertically { it } + fadeIn() with
                    slideOutVertically { -it } + fadeOut()
        } else {
            slideInVertically { -it } + fadeIn() with
                    slideOutVertically { it } + fadeOut()
        }.using(SizeTransform(clip = false))
    },
    label = "stock text"
)
*/
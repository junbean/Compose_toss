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
import androidx.compose.foundation.background
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
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
import com.junbin.compose_toss.view.ui.theme.BackgroundColor
import com.junbin.compose_toss.view.ui.theme.DecreaseTextColor
import com.junbin.compose_toss.view.ui.theme.DefaultTextColor
import com.junbin.compose_toss.view.ui.theme.IncreaseTextColor
import kotlinx.coroutines.delay

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarModule() {
    val timer: Long = 3000
    val koreaStockList = listOf(
        KoreaStockData("코스피", 2574.39, -0.03),
        KoreaStockData("코스닥", 736.36, +0.4)
    )

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(BackgroundColor),
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
    //코스피, 코스닥 리스트 인덱스
    var currentStockIndex by remember { mutableIntStateOf(0) }
    //현재 증권
    val currentStock = itemList[currentStockIndex]
    //텍스트 활성화 boolean
    var isVisible by remember { mutableStateOf(true) }
    //텍스트 색상 변경
    val textColor = if (currentStock.change > 0) IncreaseTextColor else DecreaseTextColor

    LaunchedEffect(Unit) {
        while (true) {
            //텍스트가 나오는 시간
            delay(timer)

            //timer가 끝나면 기존 텍스트 비화성화
            isVisible = false

            //300ms 뒤에 다음 텍스트 활성화
            delay(500)
            
            //다음 텍스트 지정
            currentStockIndex = (currentStockIndex + 1) % itemList.size
            
            //다음 텍스트 활성화
            isVisible = true
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
        ) {
            Row(modifier = Modifier) {
                Text(
                    text = currentStock.name,
                    color = DefaultTextColor,
                    fontSize = 16.sp
                )
                Spacer(
                    modifier = Modifier.width(6.dp)
                )
                Text(
                    text = "${currentStock.price}",
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontSize = 16.sp
                )
                Spacer(
                    modifier = Modifier.width(6.dp)
                )
                Text(
                    text = if(currentStock.change >= 0) "+${currentStock.change}%" else "${currentStock.change}%",
                    color = textColor,
                    fontSize = 16.sp
                )
            }
        }
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

/*
 - animatedVisibility
    - visible 상태에 따라 화면에서 요소가 나타나거나 사라지도록 애니메이션을 적용한다
        - visible이 true라면 enter에 지정한 애니메이션 효과에 따라 나타나고,
        - visible이 false라면 exit에 짖어한 애니메이션 효과에 따라 사라진다
    - 각 애니메이션 효과들은 서로 결합하여 적용할수 있다

 - 애니메이션 효과
    - slideInVertically()
        - 요소가 화면에 등장할 때, 수직으로 슬라이딩 애니메이션을 적용한다
        - slideInVertically(initialOffsetY = { it })
            - 요소가 화면에 등장하기 전에 어디에서 시작할지를 정의함
            - it은 요소의 높이를 나타내며, 이 경우에는 it값은 화면 아레에서 시작하여 위로 슬라이드되면서 등장한다는 의미
    - slideOutVertically()
        - 요소가 화면에서 사라질때 수직으로, 수직으로 슬라이딩 애니메이션을 적용한다
        - slideOutVertically(targetOffsetY = { -it })
            - 요소가 어디로 사라질지를 정의함
            - it은 요소의 높이를 나타내며, 이 경우에는 -it은 화면 위로 슬라이드되어 사라진다는 의미


*/
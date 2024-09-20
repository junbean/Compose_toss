package com.junbin.compose_toss.view.screens.stockPage.modules

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junbin.compose_toss.model.data.StockData
import com.junbin.compose_toss.view.ui.theme.BackgroundColor
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxColor
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxDecreaseColor
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxIconColor
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxIncreaseColor
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxTextColor
import com.junbin.compose_toss.view.ui.theme.DefaultFadedTextColor
import com.junbin.compose_toss.view.ui.theme.DefaultTextColor
import com.junbin.compose_toss.view.ui.theme.PartitionColor
import com.junbin.compose_toss.view.ui.theme.TabIndicatorColor
import com.junbin.compose_toss.view.ui.theme.TabSelectedTextColor
import com.junbin.compose_toss.view.ui.theme.TabUnselectedTextColor

@Preview(showBackground = true)
@Composable
fun TossHomeModule() {
    val sectionList =
        remember { mutableStateListOf("관심 종목", "회원님을 위한 추천 뉴스", "회원님이 관심 있어 할", "둘러보기") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            LastSeenStockModule()
            Partition()
        }

        item {
            WatchListModule()
            Partition()
        }

        item {
            RecommendNewsModule()
            Partition()
        }
    }
}


@Composable
fun LastSeenStockModule() {
    val seenStockList = remember {
        mutableStateListOf(
            StockData("삼성중공업", 0.5),
            StockData("카카오", -0.2),
            StockData("현재차", 1.8),
            StockData("LG전자", 0.5)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "최근 본 주식",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DefaultTextColor,
            modifier = Modifier.padding(start = 24.dp),
        )

        if (seenStockList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = seenStockList,
                    key = { index, stock -> stock.name }
                ) { index, stock ->
                    CommonRowItem(index, stock)
                }
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "최근 1달 이내에 본 주식이 없어요.",
                fontSize = 16.sp,
                color = DefaultFadedTextColor
            )
        }
    }
}

@Composable
fun WatchListModule() {
    val tabItems = listOf("주식", "채권", "기본")
    var selectedTabIndex by remember { mutableIntStateOf(0) }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "관심 종목",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DefaultTextColor,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundColor)
                .padding(all = 16.dp),
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                containerColor = BackgroundColor,
                contentColor = TabUnselectedTextColor,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = TabIndicatorColor
                    )
                },
            ) {
                tabItems.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedTabIndex == index) TabSelectedTextColor else TabUnselectedTextColor
                            )
                        },
                    )
                }
            }

            // 선택된 탭에 따른 내용 표시
            /*
            when (selectedTabIndex) {
                0 ->
                1 ->
                2 ->
            }

             */
        }
    }
}

@Composable
fun RecommendNewsModule() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "회원님을 위한 추천 뉴스",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DefaultTextColor,
            modifier = Modifier.padding(start = 24.dp),
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(24.dp))

        }


    }
}


@Composable
fun Partition() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(PartitionColor)
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CommonRowItem(index: Int, stockData: StockData) {
    //누름 상태 저장
    var isPressed by remember { mutableStateOf(false) }

    //Row 크기 조정 애니메이션
    val rowScale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        label = "Row Scale Animation"
    )

    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease() // 사용자가 눌렀다가 뗄 때까지 기다림
                        isPressed = false
                    }
                )
            }
            .scale(rowScale),
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = CommonRowBoxColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stockData.name,
                color = CommonRowBoxTextColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (stockData.change > 0) "+${stockData.change}%" else "${stockData.change}%",
                color = if (stockData.change > 0) CommonRowBoxIncreaseColor else CommonRowBoxDecreaseColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(4.dp))
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = { }
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = CommonRowBoxIconColor
                    )
                }
            }
        }
    }
}


/*
Lazy(Column/Row/grids)
    - 여러개의 데이터 리스트를 View로 나타낼때 사용됨, 리사이클러뷰와 비슷한 기능
    - LazyColumn
        - 여러개의 데이터 리스트를 뷰 형태로 수직으로 나열
        - LazyItemScope
        - item
            - LazyColumn 내부에 컴포저블을 직접 넣고 싶을때 사용하는 메소드
            - LazyItemScope에 있는 컴포저블이 하나의 Item으로 인식된다
            - ex)
                    fun MessageList(){
                        LazyColumn{
                            item{
                                Text("First item")
                            }

                            items(5){ index ->
                                Text(text = "item :$index")
                            }

                            item{
                                Text(text="Last Item")
                            }
                        }
                    }



        - items
            - 컴포저블을 반복해서 나타내고자 할때 사용
        - itemsIndexed
            - 실제 제품을 개발할 때 가장 많이 쓰이는 방식
            - 아이템으로 Custom한 클래스의 설정이 가능
            - key 값을 넣는다
                - 인덱스의 key값을 알아서 변화가 되었을때 그 데이터 값만 리컴포즈가 되어 효율적으로 철할 수 있다.
                - DiffUtil 같은 역할
    - LazyRow
    - LazyGrids


item vs items/itemsIndexed:
    - item은 단일 아이템을 추가하는 반면, items와 itemsIndexed는 여러 아이템을 한 번에 추가합니다.
    - item은 고정된 내용에 적합하고, items/itemsIndexed는 동적인 리스트에 적합합니다.


items vs itemsIndexed:
    - items는 아이템의 내용만 제공하는 반면, itemsIndexed는 아이템의 내용과 인덱스를 함께 제공합니다.
    - itemsIndexed는 아이템의 위치 정보가 필요한 경우(예: 번호 매기기, 특정 위치의 아이템 스타일 변경 등)에 유용합니다.

성능:
    - item은 단일 아이템을 추가하므로 여러 개의 고정된 아이템을 추가할 때 성능이 떨어질 수 있습니다.
    - items와 itemsIndexed는 내부적으로 최적화되어 있어 대량의 아이템을 효율적으로 처리할 수 있습니다.

유연성:
    - item은 각 아이템마다 다른 레이아웃을 쉽게 적용할 수 있습니다.
    - items와 itemsIndexed는 동일한 유형의 아이템을 반복적으로 표시하는 데 더 적합합니다.

    - 상황에 따라 적절한 방법을 선택하여 사용하면 됩니다.
    - 고정된 단일 아이템은 item을,
    - 동적인 리스트는 items를,
    - 그리고 인덱스가 필요한 경우 itemsIndexed를 사용하는 것이 좋습니다.
*/
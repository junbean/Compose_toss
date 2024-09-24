package com.junbin.compose_toss.view.screens.stockPage

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junbin.compose_toss.R
import com.junbin.compose_toss.view.components.NoRippleInteractionSource
import com.junbin.compose_toss.view.components.TopBarModule
import com.junbin.compose_toss.view.components.customTabIndicatorOffset
import com.junbin.compose_toss.view.screens.stockPage.modules.DiscoveryModule
import com.junbin.compose_toss.view.screens.stockPage.modules.NewsModule
import com.junbin.compose_toss.view.screens.stockPage.modules.TossHomeModule
import com.junbin.compose_toss.view.ui.theme.BackgroundColor
import com.junbin.compose_toss.view.ui.theme.DefaultDarkTextColor
import com.junbin.compose_toss.view.ui.theme.DivideLineColor
import com.junbin.compose_toss.view.ui.theme.TabIndicatorColor
import com.junbin.compose_toss.view.ui.theme.TabSelectedTextColor
import com.junbin.compose_toss.view.ui.theme.TabUnselectedTextColor

@Preview
@Composable
fun StockPage() {
    Scaffold(
        topBar = {
            TopBarModule()
        },

        ) { innerPadding ->
        CertificateModule(Modifier.padding(innerPadding))
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CertificateModule(modifier: Modifier = Modifier) {
    val tabItems = listOf("토스증권 홈", "발견", "뉴스")
    var selectedTabIndex by remember { mutableIntStateOf(0) }


    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabItems.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        item {
            QuizModule()
        }

        item {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                containerColor = BackgroundColor,
                contentColor = TabUnselectedTextColor,
                edgePadding = 8.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier
                            .customTabIndicatorOffset(
                                currentTabPosition = tabPositions[selectedTabIndex],
                                tabWidth = tabWidths[selectedTabIndex]
                            )
                            .width(width = tabPositions[selectedTabIndex].width),       // 각 Tab의 너비에 맞추어 인디케이터 너비 설정
                        color = TabIndicatorColor
                    )
                },
                divider = {  }
            ) {
                tabItems.forEachIndexed { index, title ->
                    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            interactionSource = NoRippleInteractionSource(),
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedTabIndex == index) TabSelectedTextColor else TabUnselectedTextColor,
                                    onTextLayout = { textLayoutResult ->
                                        tabWidths[index] = with(density) { textLayoutResult.size.width.toDp() }
                                    }
                                )
                            },
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(DivideLineColor),
            )
        }

        item {
            when (selectedTabIndex) {
                0 -> TossHomeModule()
                1 -> DiscoveryModule()
                2 -> NewsModule()
            }
        }

        item {
            StockFooterModule()
        }
    }
}

@Composable
fun StockFooterModule() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizModule() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFEFEFEF))
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_quiz_mark),
                contentDescription = "quiz image",
                modifier = Modifier.size(60.dp)
            )
            Text(
                text = "퀴즈 맞히면\n주식 최대 100만원",
                fontSize = 24.sp,
                color = DefaultDarkTextColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(Color.Blue),
                shape = RoundedCornerShape(8.dp),
                //버튼 안의 하위객체의 padding을 정의
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(
                    text = "퀴즈 풀러가기",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.wrapContentHeight()
                )
            }

        }
    }
}





/*

scaffold에서 innerpadding을 사용해서 content 컴포넌트의 modifier에 지정을 해야 topbar를 제외한 공간을 사용할수 있다
innerpadding을 사용하지 않으면 topbar를 포함한 구역을 써서 위젯이 가려질수 있다

scaffold
    - topBar
    - bottomBar
    - floatingActionButton
    - content
    

TabRow
    - 

Column에 스크롤 만들기
    - lazy Column
    - Modifier.verticalScroll(rememberScrollState()) 지정
*/
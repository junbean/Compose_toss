package com.junbin.compose_toss.view.screens.stockPage

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junbin.compose_toss.R
import com.junbin.compose_toss.view.common.NoRippleInteractionSource
import com.junbin.compose_toss.view.components.TopBarModule
import com.junbin.compose_toss.view.common.customTabIndicatorOffset
import com.junbin.compose_toss.view.screens.stockPage.modules.DiscoveryModule
import com.junbin.compose_toss.view.screens.stockPage.modules.NewsModule
import com.junbin.compose_toss.view.screens.stockPage.modules.TossHomeModule
import com.junbin.compose_toss.view.ui.theme.BackgroundColor
import com.junbin.compose_toss.view.ui.theme.BrightGrayBackgroundColor
import com.junbin.compose_toss.view.ui.theme.DefaultDarkTextColor
import com.junbin.compose_toss.view.ui.theme.DefaultGrayTextColor
import com.junbin.compose_toss.view.ui.theme.DivideLineColor
import com.junbin.compose_toss.view.ui.theme.FooterBackgroundColor
import com.junbin.compose_toss.view.ui.theme.FooterFocusTextColor
import com.junbin.compose_toss.view.ui.theme.FooterIconColor
import com.junbin.compose_toss.view.ui.theme.FooterTextColor
import com.junbin.compose_toss.view.ui.theme.TabIndicatorColor
import com.junbin.compose_toss.view.ui.theme.TabSelectedTextColor
import com.junbin.compose_toss.view.ui.theme.TabUnselectedTextColor

@Preview
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
                divider = { }
            ) {
                tabItems.forEachIndexed { index, title ->
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
                                    tabWidths[index] =
                                        with(density) { textLayoutResult.size.width.toDp() }
                                }
                            )
                        },
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(DivideLineColor),
            )
        }

        when (selectedTabIndex) {
            0 -> {
                item {
                    TossHomeModule()
                }
            }

            1 -> {
                item {
                    DiscoveryModule()
                }
            }

            2 -> {
                item {
                    NewsModule()
                }
            }
        }

        item {
            StockFooterModule()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StockFooterModule() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(color = FooterBackgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = " ",
                tint = FooterIconColor
            )
            Text(
                text = "토스 증권",
                color = DefaultGrayTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "토스 증권에서 제공하는 투자 정보는 고객의 투자 판단을 위한 단순 참고용일뿐, 투자 제안 밒  권유·종목 추천을 위해 작성된 것이 아닙니다.",
            fontSize = 14.sp,
            color = FooterTextColor

        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "고객센터  |  투자 유의사항  |  개인정보처리방침",
            color = DefaultGrayTextColor,
            fontSize = 14.sp
        )
        Text(
            text = "이용자권리 및 유의사항  |  신용정보 활용체제",
            color = DefaultGrayTextColor,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "꼭 알아두세요", fontSize = 16.sp, color = FooterFocusTextColor)
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = " "
            )
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
                .background(BrightGrayBackgroundColor)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_quiz_mark),
                contentDescription = "quiz image",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "퀴즈 맞히면\n주식 최대 100만원",
                fontSize = 24.sp,
                color = DefaultDarkTextColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
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
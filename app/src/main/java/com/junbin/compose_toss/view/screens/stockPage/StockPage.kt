package com.junbin.compose_toss.view.screens.stockPage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junbin.compose_toss.R
import com.junbin.compose_toss.view.components.TopBarModule
import com.junbin.compose_toss.view.screens.stockPage.modules.DiscoveryModule
import com.junbin.compose_toss.view.screens.stockPage.modules.NewsModule
import com.junbin.compose_toss.view.screens.stockPage.modules.TossHomeModule
import com.junbin.compose_toss.view.ui.theme.BackgroundColor
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
@OptIn(ExperimentalFoundationApi::class)
fun CertificateModule(modifier: Modifier = Modifier) {
    val tabItems = listOf("토스증권 홈", "발견", "뉴스")
    var selectedTabIndex by remember { mutableIntStateOf( 0 ) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = BackgroundColor,
            contentColor = TabUnselectedTextColor,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex]) // Tab의 위치에 맞춰 이동
                        .width(tabPositions[selectedTabIndex].width),       // 각 Tab의 너비에 맞추어 인디케이터 너비 설정
                    color = TabIndicatorColor
                )
            },
            divider = { }
        ) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.wrapContentWidth(Alignment.Start),
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

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(DivideLineColor),
        )


        when (selectedTabIndex) {
            0 -> TossHomeModule()
            1 -> DiscoveryModule()
            2 -> NewsModule()
        }

        StockFooterModule()
    }
}

@Composable
fun StockFooterModule() {

}

@Composable
fun QuizModule() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.quiz_img),
                contentDescription = "quiz image",
                modifier = Modifier.size(80.dp)
            )


        }
    }
}


/*
TabRow(
    selectedTabIndex = selectedTabIndex,
    modifier = Modifier.fillMaxWidth(),
    containerColor = BackgroundColor,
    contentColor = TabTextColor,
    indicator = { tabPositions ->
        TabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
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
                )
            },
            modifier = Modifier.wrapContentWidth(align = Alignment.Start),
        )
    }
}


scaffold에서 innerpadding을 사용해서 content 컴포넌트의 modifier에 지정을 해야 topbar를 제외한 공간을 사용할수 있다
innerpadding을 사용하지 않으면 topbar를 포함한 구역을 써서 위젯이 가려질수 있다

scaffold
    - topBar
    - bottomBar
    - content
    

TabRow
    - 
*/
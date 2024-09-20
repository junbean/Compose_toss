package com.junbin.compose_toss.view.screens.stockPage

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.junbin.compose_toss.view.components.TopBarModule
import com.junbin.compose_toss.view.screens.stockPage.modules.DiscoveryModule
import com.junbin.compose_toss.view.screens.stockPage.modules.NewsModule
import com.junbin.compose_toss.view.screens.stockPage.modules.TossHomeModule
import com.junbin.compose_toss.view.ui.theme.BackgroundColor
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
fun CertificateModule(modifier: Modifier = Modifier) {
    val tabItems = listOf("토스증권 홈", "발견", "뉴스")
    var selectedTabIndex by remember { mutableIntStateOf(0) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
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
        when (selectedTabIndex) {
            0 -> TossHomeModule()
            1 -> DiscoveryModule()
            2 -> NewsModule()
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
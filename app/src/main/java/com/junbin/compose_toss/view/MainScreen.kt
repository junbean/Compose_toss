package com.junbin.compose_toss.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.junbin.compose_toss.R
import com.junbin.compose_toss.model.data.NavItem
import com.junbin.compose_toss.view.components.TopBarModule
import com.junbin.compose_toss.view.screens.BoonPage
import com.junbin.compose_toss.view.screens.HomePage
import com.junbin.compose_toss.view.screens.MenuPage
import com.junbin.compose_toss.view.screens.stockPage.StockPage
import com.junbin.compose_toss.view.screens.TossPayPage
import com.junbin.compose_toss.view.ui.theme.BottomNavigationBorderColor


@Preview
@Composable
fun MainScreen() {
    val navItemList = listOf(
        NavItem("홈", R.drawable.ic_home),
        NavItem("혜택", R.drawable.ic_diamond),
        NavItem("토스페이", R.drawable.ic_toss_pay),
        NavItem("증권", R.drawable.ic_chart),
        NavItem("전체", R.drawable.ic_menu)
    )

    var selectedIndex by remember {
        mutableIntStateOf(3)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarModule()
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .border(
                        width = 1.dp,
                        color = BottomNavigationBorderColor,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        modifier = Modifier,
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(
                                painterResource(id = navItem.icon),
                                contentDescription = "Icon",
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        },
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> HomePage()
        1 -> BoonPage()
        2 -> TossPayPage()
        3 -> StockPage()
        4 -> MenuPage()
    }
}
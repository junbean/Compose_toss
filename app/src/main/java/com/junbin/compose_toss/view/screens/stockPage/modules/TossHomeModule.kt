package com.junbin.compose_toss.view.screens.stockPage.modules

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junbin.compose_toss.R
import com.junbin.compose_toss.model.data.LooAroundData
import com.junbin.compose_toss.model.data.NewsData
import com.junbin.compose_toss.model.data.StockData
import com.junbin.compose_toss.view.components.CommonRowItem
import com.junbin.compose_toss.view.ui.theme.BackgroundColor
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxColor
import com.junbin.compose_toss.view.ui.theme.DecreaseStockBoxColor
import com.junbin.compose_toss.view.ui.theme.DecreaseTextColor
import com.junbin.compose_toss.view.ui.theme.DefaultDarkTextColor
import com.junbin.compose_toss.view.ui.theme.DefaultGrayTextColor
import com.junbin.compose_toss.view.ui.theme.DefaultTextColor
import com.junbin.compose_toss.view.ui.theme.DivideLineColor
import com.junbin.compose_toss.view.ui.theme.IncreaseStockBoxColor
import com.junbin.compose_toss.view.ui.theme.IncreaseTextColor
import com.junbin.compose_toss.view.ui.theme.NoChangeTextColor
import com.junbin.compose_toss.view.ui.theme.PartitionColor
import com.junbin.compose_toss.view.ui.theme.TabIndicatorColor
import com.junbin.compose_toss.view.ui.theme.TabSelectedTextColor
import com.junbin.compose_toss.view.ui.theme.TabUnselectedTextColor

@Preview(showBackground = true)
@Composable
fun TossHomeModule() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LastSeenStockModule()
        Partition()

        WatchListModule()
        Partition()

        RecommendNewsModule()
        Partition()

        InterestedStockModule()
        Partition()

        LookAroundModule()
    }
}

//최근 본 주식
@Composable
fun LastSeenStockModule() {
    val seenStockList = remember {
        mutableStateListOf(
            StockData("삼성중공업", 1000, 0.5, "원", R.drawable.stock_samsung_img),
            StockData("카카오", 1000, -0.2, "원", R.drawable.stock_samsung_img),
            StockData("현재차", 1000, 1.8, "원", R.drawable.stock_samsung_img),
            StockData("LG전자", 1000, 0.5, "원", R.drawable.stock_samsung_img)
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
                color = DefaultGrayTextColor
            )
        }
    }
}

//관심 종목
@Composable
fun WatchListModule() {
    val tabItems = listOf("주식", "채권", "기본")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val stockList = listOf(
        StockData("삼성중공업", 10920, 2.7, "원", R.drawable.stock_samsung_img),
        StockData("하이브", 158000, -1.2, "원", R.drawable.stock_hybe_img)
    )

    val bondList = listOf(
        StockData("신세계", 150000, 3.5, "원", R.drawable.ic_launcher_background),
        StockData("LG디스플레이", 23000, -0.8, "원", R.drawable.ic_launcher_background),
        StockData("SK하이닉스", 85000, 1.5, "원", R.drawable.ic_launcher_background)
    )

    val basicList = listOf(
        StockData("LG전자", 100000, 2.5, "원", R.drawable.ic_launcher_background),
        StockData("카카오", 120000, -1.2, "원", R.drawable.ic_launcher_background),
        StockData("삼성전자", 75000, 0.5, "원", R.drawable.ic_launcher_background),
        StockData("현대차", 200000, 1.8, "원", R.drawable.ic_launcher_background)
    )

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
                .padding(all = 24.dp),
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
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = TabIndicatorColor
                    )
                },
                divider = { }
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

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(DivideLineColor),
            )


            // 선택된 탭에 따른 내용 표시
            when (selectedTabIndex) {
                0 ->
            }
        }
    }
}


//회원님을 위한 추천 뉴스
@Composable
fun RecommendNewsModule() {
    val newsList = listOf(
        NewsData(
            listOf(
                StockData("삼성전자", 1000, 0.1, "원", R.drawable.stock_samsung_img)
            ),
            R.drawable.stock_news_img2,
            "삼성전자, 증권가 목표가 하향 소식에 약세",
            "이투데이",
            1,
        ),
        NewsData(
            listOf(
                StockData("SK이노베이션우", 1000, 0.3, "원", R.drawable.stock_samsung_img),
                StockData("현대차", 1000, -0.9, "원", R.drawable.stock_samsung_img),
            ),
            R.drawable.stock_news_img3,
            "\"연내 흑자 전환 목표\" SK온, 10둴 美서 현대차 배터리 만든다",
            "서울경제",
            2
        ),
        NewsData(
            listOf(
                StockData("카카오", 1000, 0.0, "원", R.drawable.stock_samsung_img),
            ),
            R.drawable.stock_news_img1,
            "카카오페이證, 美주식 매매 수수료율 0.01%로 추가 인상",
            "조선비즈",
            23
        ),
        NewsData(
            listOf(
                StockData("삼성전자", 1000, 0.4, "원", R.drawable.stock_samsung_img),
            ),
            R.drawable.stock_news_img5,
            "\"TSMC·삼성전자, UAE에 대형 반도체 공장 설립\"",
            "파이낸셜뉴스",
            1
        ),
        NewsData(
            listOf(
                StockData("LG전자", 1000, 0.1, "원", R.drawable.stock_samsung_img),
            ),
            R.drawable.stock_news_img4,
            "\"느려도 괜칞아\" ...LG전자, \'아동 맞춤\' 눈높이 가전 교율",
            "머니투데이",
            23
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "회원님을 위한 추천 뉴스",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DefaultTextColor,
                modifier = Modifier,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "관심 가질만한 뉴스만 모았어요",
                fontSize = 12.sp,
                color = DefaultGrayTextColor,
                modifier = Modifier,
            )
        }

        newsList.forEach { news ->
            NewsPartModule(news)
        }

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(DivideLineColor)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "더 보기",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DefaultGrayTextColor,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


//관심 있어 할 주식 목록
@Composable
fun InterestedStockModule() {
    val stockList = listOf(
        StockData("삼성전자", 62500, -0.6, "원", R.drawable.stock_samsung_img),
        StockData("SK하이닉스", 162000, 3.1, "원", R.drawable.stock_sk_hynix_img),
        StockData("한미반도체", 101300, 0.8, "원", R.drawable.stock_hanmi_img),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "회원님이 관심 있어 할 \n반도체 관련 주식",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DefaultTextColor,
                modifier = Modifier,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "최근 찾아 본 주식을 분석했어요.",
                fontSize = 12.sp,
                color = DefaultGrayTextColor,
                modifier = Modifier,
            )
        }

        stockList.forEach { stock ->
            StockPartModule(stock)
        }

    }
}

//둘러보기
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun LookAroundModule() {
    val lookAroundList = listOf(
        LooAroundData("오늘도", "출석체크\n 하러가기", R.drawable.ic_up_chart),
        LooAroundData("차곡차곡", "주식\n 모으기", R.drawable.ic_layer),
        LooAroundData("이자 받는", "해외채권\n 보러가기", R.drawable.ic_money),
        LooAroundData("실시간", "거래량 많은\n 주식보기", R.drawable.ic_fire),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "둘러보기",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DefaultTextColor,
                modifier = Modifier,
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp), //lazyRow 자체 패딩
            horizontalArrangement = Arrangement.spacedBy(16.dp)  //내부 아이템 패딩
        ) {
            itemsIndexed(lookAroundList) { index, item ->
                LookAroundBoxModule(item)
            }

        }
    }
}

//경계선
@Composable
fun Partition() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(PartitionColor)
    )
}

//뉴스 모듈
@Composable
fun NewsPartModule(news: NewsData) {
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            //주식 목록
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                news.stock.forEach { stock ->
                    Text(
                        text = stock.name,
                        fontSize = 14.sp,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = when {
                            stock.change > 0 -> "+${stock.change}%"
                            stock.change == 0.0 -> "${stock.change}%"
                            else -> "${stock.change}%"
                        },
                        color = when {
                            stock.change > 0 -> IncreaseTextColor
                            stock.change == 0.0 -> NoChangeTextColor
                            else -> DecreaseTextColor
                        },
                        fontSize = 14.sp,
                    )
                }
            }
            //기사 내용
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = news.newsContent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${news.news}  ·  ${news.hour}시간 전",
                        color = DefaultDarkTextColor
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = news.newsImg),
                    contentDescription = "news image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsPartModule() {
    val news = NewsData(
        listOf(
            StockData("삼성전자", 62500, -0.6, "원", R.drawable.stock_samsung_img),
            StockData("SK하이닉스", 162000, 3.1, "원", R.drawable.stock_samsung_img),
            StockData("한미반도체", 101300, 0.8, "원", R.drawable.stock_samsung_img),
        ),
        R.drawable.stock_news_img3,
        "\"연내 흑자 전환 목표\" SK온, 10월 美서 현대차 배터리 만든다",
        "서울경제",
        2
    )

    NewsPartModule(news)
}

@Composable
fun StockPartModule(stock: StockData) {
    //누름 상태 저장
    var isPressed by remember { mutableStateOf(false) }

    //Row 크기 조정 애니메이션
    val rowScale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        label = "Row Scale Animation"
    )

    Box(
        modifier = Modifier
            .scale(rowScale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease() // 사용자가 눌렀다가 뗄 때까지 기다림
                        isPressed = false
                    }
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = stock.stockImage),
                contentDescription = "stock image",
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = stock.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = DefaultDarkTextColor
                )

                Text(
                    text = stock.price.toString(),
                    fontSize = 14.sp,
                    color = DefaultDarkTextColor
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        color =
                        if (stock.change > 0)
                            IncreaseStockBoxColor
                        else
                            DecreaseStockBoxColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = when {
                        stock.change > 0 -> "+${stock.change}%"
                        else -> "${stock.change}%"
                    },
                    color = when {
                        stock.change > 0 -> IncreaseTextColor
                        stock.change == 0.0 -> NoChangeTextColor
                        else -> DecreaseTextColor
                    },
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStockPartModule() {
    val stockItem = StockData("삼성전자", 62500, -0.6, "원", R.drawable.stock_samsung_img)

    StockPartModule(stockItem)
}

@Composable
fun LookAroundBoxModule(item: LooAroundData) {
    var isPressed by remember { mutableStateOf(false) }

    val rowScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "Row Scale Animation"
    )

    Box(
        modifier = Modifier
            .scale(rowScale)
            .background(
                color = CommonRowBoxColor,
                shape = RoundedCornerShape(16.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease() // 사용자가 눌렀다가 뗄 때까지 기다림
                        isPressed = false
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .width(130.dp)
                .padding(16.dp)
        ) {
            Text(
                text = item.title,
                color = DefaultGrayTextColor,
                fontSize = 14.sp
            )
            Text(
                text = item.content,
                color = DefaultTextColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(
                modifier = Modifier.height(24.dp),
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    painter = painterResource(item.image),
                    contentDescription = "icon",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLookAroundBoxModule() {
    val item = LooAroundData("오늘도", "출석체크 \n하러가기", R.drawable.ic_up_chart)
    LookAroundBoxModule(item)
}

@Composable
fun WatchListPartModule(title: String, itemList: List<StockData>) {
    var isPressed by remember { mutableStateOf(false) }
    var isMore = false

    val rowScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "Row Scale Animation"
    )

    Column {
        Box(
            modifier = Modifier
                .scale(rowScale)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease() // 사용자가 눌렀다가 뗄 때까지 기다림
                            isPressed = false
                        }
                    )
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = title, color = DefaultDarkTextColor, fontSize = 18.sp, )
                Icon(
                    Icon = Icons.Default.KeyboardArrowDown,
                    contentDescription = "news image",
                )
            }

        }

        if (!isMore) {
            itemList.forEach { item ->

            }
        }
    }
}



/*
LazyColumn(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    itemsIndexed(newsList) { index, item ->
        NewsPartModule(item)
    }
}


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
                    ==========================================


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
    
    
    
추가)
lazy column 내부에 lazy column을 넣으면 에러가 발생한다
그래서 이 페이지의 경우에는 lazy column안에 foreach를 사용해서 아이템을 나열함

레이아웃 안의 가장 큰 요소의 높이를 레이아웃의 높이로 지정하고 싶다면
상위 레이아웃에 modifier = Modifier.height(IntrinsicSize.Max를 지정한다
그러면 자동으로 내부 요소중 가장 큰 객체의 높이에 맞춰진다
그래서 내부객체에게 Modifier.fillMaxHeight를 부여해도 화면을 가릴정도로 길어지지 않음
Row(
    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max)
) {
    Image(
        painter = painterResource(id = stock.stockImage),
        modifier = Modifier.size(56.dp).clip(CircleShape).fillMaxHeight(),
    )

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = stock.name,)
        Text(text = stock.price.toString(),)
    }

    Box(modifier = Modifier) { .. }
}


*/
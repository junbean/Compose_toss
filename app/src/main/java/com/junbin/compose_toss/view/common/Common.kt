package com.junbin.compose_toss.view.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junbin.compose_toss.model.data.StockData
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxColor
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxDecreaseColor
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxIconColor
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxIncreaseColor
import com.junbin.compose_toss.view.ui.theme.CommonRowBoxTextColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun CommonImage(
    imageResId: Int,
    contentDescription: String? = null,
    modifier: Modifier,
) {
    val imageBitmap = ImageBitmap.imageResource(id = imageResId)

    Image(
        bitmap = imageBitmap,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun AdjustableImage(
    imageResId: Int,
    contentDescription: String? = null,
    modifier: Modifier,
    contentScale: ContentScale,
) {
    val imageBitmap = ImageBitmap.imageResource(id = imageResId)
    val imageRatio = imageBitmap.width.toFloat() / imageBitmap.height.toFloat()

    Image(
        bitmap = imageBitmap,
        contentDescription = contentDescription,
        modifier = modifier
            .aspectRatio(imageRatio),
        contentScale = contentScale
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

//modifier
fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        label = " ",
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing,
        )
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        label = " ",
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}


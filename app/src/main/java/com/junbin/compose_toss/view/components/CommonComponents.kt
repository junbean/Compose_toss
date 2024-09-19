package com.junbin.compose_toss.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource

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
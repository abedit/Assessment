package com.abedit.aldiassessment.ui.coinDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abedit.aldiassessment.ui.theme.CoinDetailsItemBackground

/**
 * The rounded box in the details screen
 */
@Composable
fun CoinDetailsBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    roundedCornerShape: RoundedCornerShape,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 30.dp)
            .background(CoinDetailsItemBackground, roundedCornerShape),
        contentAlignment = contentAlignment
    ) {
        content()
    }
}



@Preview
@Composable
private fun DefaultPreview() {
    CoinDetailsBox(
        roundedCornerShape = RoundedCornerShape(12.dp),
    ) {

    }
}
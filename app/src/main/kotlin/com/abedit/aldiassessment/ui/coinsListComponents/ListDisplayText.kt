package com.abedit.aldiassessment.ui.coinsListComponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.abedit.aldiassessment.ui.theme.CoinDisplayText

@Composable
fun ListDisplayText(
    modifier: Modifier,
    text: String,
    fontSize: TextUnit,
    textColor: Color = CoinDisplayText
) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        fontSize = fontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
        color = textColor
    )
}

@Preview(showBackground = true)
@Composable
private fun ListDisplayTextPreview() {
    ListDisplayText(
        modifier = Modifier,
        text = "Bitcoin",
        fontSize = 19.sp
    )
}
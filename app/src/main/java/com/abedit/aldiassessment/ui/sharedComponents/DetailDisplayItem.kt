package com.abedit.aldiassessment.ui.sharedComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abedit.aldiassessment.ui.theme.CoinDisplayText

@Composable
fun DisplayItem(
    caption: String,
    value: String,
    textColor: Color = CoinDisplayText
) {
    Row(
        modifier = Modifier.padding(top = 7.dp)
    ) {
        Text(
            text = caption,
            modifier = Modifier.weight(0.6f),
            fontSize = 16.sp,
            color = CoinDisplayText
        )

        Text(
            text = value,
            modifier = Modifier.weight(0.4f),
            textAlign = TextAlign.End,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview
private fun DisplayItemPreview() {
    DisplayItem(caption = "Price", value = "$3K")
}

package com.abedit.aldiassessment.ui.sharedComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abedit.aldiassessment.R
import com.abedit.aldiassessment.ui.theme.TextColor
import com.abedit.aldiassessment.ui.theme.ToolbarBackground


@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String,
    showBackButton: Boolean = false,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(ToolbarBackground)
            .padding(15.dp)
    ) {

        AnimatedVisibility(
            visible = showBackButton,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally(),
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(20.dp),
                painter = painterResource(R.drawable.arrow_back),
                tint = Color.Black,
                contentDescription = null
            )
        }



        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = TextColor
            ),
            fontSize = 18.sp
        )
    }


}

@Preview(showBackground = true)
@Composable
fun Preview_HomeScreen() {
    Toolbar(title = "COINS")
}

@Preview(showBackground = true)
@Composable
fun Preview_DetailsScreen() {
    Toolbar(title = "Ethereum", showBackButton = true)
}
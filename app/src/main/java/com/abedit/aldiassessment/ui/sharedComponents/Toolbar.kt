package com.abedit.aldiassessment.ui.sharedComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abedit.aldiassessment.R
import com.abedit.aldiassessment.ui.theme.CoinDisplayText
import com.abedit.aldiassessment.ui.theme.ToolbarBackground
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String,
    backButtonClicked: (() -> Unit)? = null
) {

    val toolbarHeight = 50.dp
    var isClickable by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(toolbarHeight)
            .background(ToolbarBackground)
    ) {

        AnimatedVisibility(
            visible = backButtonClicked != null
        ) {
            //I prefer to have the back button take the clickable
            // area to have the entire height of the toolbar
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable(enabled = isClickable) {
                        //To avoid repetitive clicks in quick succession
                        if (isClickable) {
                            isClickable = false
                            backButtonClicked?.invoke()
                            coroutineScope.launch {
                                delay(800L)
                                isClickable = true
                            }
                        }
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxHeight(),
                    painter = painterResource(R.drawable.arrow_back),
                    tint = Color.Black,
                    contentDescription = title,
                )
            }

        }



        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = if (backButtonClicked != null) 5.dp else 10.dp),
            fontWeight = FontWeight.Bold,
            color = CoinDisplayText,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
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
    Toolbar(title = "Ethereum", backButtonClicked = {})
}
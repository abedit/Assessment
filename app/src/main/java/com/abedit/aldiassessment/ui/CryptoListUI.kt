package com.abedit.aldiassessment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abedit.aldiassessment.ui.sharedComponents.Toolbar
import com.abedit.aldiassessment.ui.theme.AldiAssessmentTheme
import com.abedit.aldiassessment.ui.theme.CoinsListBackground
import com.abedit.aldiassessment.ui.theme.ToolbarBackground

@Composable
fun CoinsOverview() {
    AldiAssessmentTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                Toolbar(
                    modifier = Modifier.background(ToolbarBackground),
                    title = "COINS",
                    showBackButton = true
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CoinsListBackground)
                ) {
                    CoinsList()
                }

            }
        }

    }

}

@Composable
private fun CoinsList() {
    val listItems: List<String> = listOf("Bitcoin", "Ethereum", "XRP")
    LazyColumn() {
        items(items = listItems) { mItem ->
            Text(
                text = mItem,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AldiAssessmentTheme {
        CoinsOverview()
    }
}
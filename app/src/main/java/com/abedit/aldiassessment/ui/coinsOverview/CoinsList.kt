package com.abedit.aldiassessment.ui.coinsOverview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abedit.aldiassessment.getPreviewCoin
import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.overviewData.ListUiState
import com.abedit.aldiassessment.ui.CoinsOverview

@Composable
fun CoinsList(coinsList: List<Coin>) {
    //show the list of coins
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = coinsList) { mItem ->
            Text(
                text = mItem.id,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultPreview() {
    CoinsList(listOf(getPreviewCoin()))
}
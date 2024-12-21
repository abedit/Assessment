package com.abedit.aldiassessment.ui.coinsOverview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abedit.aldiassessment.R
import com.abedit.aldiassessment.getPreviewCoin
import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.ui.theme.CoinListItemBackground

@Composable
fun CoinsList(coinsList: List<Coin>) {
    //show the list of coins
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 6.dp)
    ) {
        items(items = coinsList) { mItem ->
            Box(
                modifier = Modifier.padding(vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(CoinListItemBackground, RoundedCornerShape(12.dp))
                        .fillParentMaxWidth()
                        .padding(10.dp)
                ) {

                    //icon

                    Text(
                        text = mItem.id,
                        modifier = Modifier.padding(15.dp)
                    )
                }
            }


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultPreview() {
    CoinsList(listOf(getPreviewCoin(), getPreviewCoin(), getPreviewCoin()))
}
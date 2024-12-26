package com.abedit.aldiassessment.ui.coinsListComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abedit.aldiassessment.NULL_VALUE_PLACEHOLDER
import com.abedit.aldiassessment.coinId2IconId
import com.abedit.aldiassessment.formattedPercentage
import com.abedit.aldiassessment.formattedPrice
import com.abedit.aldiassessment.getPreviewCoin
import com.abedit.aldiassessment.hasPercentageIncreased
import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.ui.theme.CoinListItemBackground
import com.abedit.aldiassessment.ui.theme.CoinsListBackground
import com.abedit.aldiassessment.ui.theme.Green
import com.abedit.aldiassessment.ui.theme.Red

@Composable
fun CoinsList(
    coinsList: List<Coin>,
    lazyColumnState: LazyListState,
    navigateToDetails: (Coin) -> Unit
) {
    //show the list of coins
    /*
    * CoinItem structure
    * Row
    *   Icon  Column   Column
    * */
    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 6.dp)
    ) {
        items(items = coinsList) { mCoin ->
            CoinListItem(
                mCoin = mCoin,
                modifier = Modifier.clickable {
                    navigateToDetails(mCoin)
                })
        }
    }
}

@Composable
private fun CoinListItem(mCoin: Coin, modifier: Modifier) {
    Box(
        modifier = modifier
            .padding(vertical = 6.dp)
    ) {

        /*
        * Row:
        * Icon  Column  Column
        *       Text    Text
        *       Text    Text
        */
        Row(
            modifier = Modifier
                .background(CoinListItemBackground, RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .padding(horizontal = 11.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CoinIcon(
                coinId = mCoin.id,
                modifier = Modifier.align(Alignment.CenterVertically)
            )


            CoinBasicInfo(
                coinId = mCoin.name?: NULL_VALUE_PLACEHOLDER,
                coinSymbol = mCoin.symbol ?: NULL_VALUE_PLACEHOLDER,
                modifier = Modifier.weight(1f)
            )

            CoinPrice(
                coinPriceUsd = mCoin.priceUsd.formattedPrice(),
                coinChangePercent = mCoin.changePercent24Hr.formattedPercentage(),
                hasIncreased = mCoin.changePercent24Hr.hasPercentageIncreased,
                modifier = Modifier.weight(0.5f)
            )

        }
    }
}

@Composable
private fun CoinIcon(coinId: String, modifier: Modifier) {
    Icon(
        painter = painterResource(id = coinId.coinId2IconId()),
        contentDescription = "",
        modifier = modifier.size(50.dp),
        tint = Color.Unspecified
    )
}

/*
* For the name and symbol */
@Composable
private fun CoinBasicInfo(
    coinId: String,
    coinSymbol: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 15.dp, top = 5.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {

        ListDisplayText(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp),
            text = coinId.uppercase(),
            fontSize = 19.sp,
        )

        ListDisplayText(
            modifier = Modifier
                .padding(top = 3.dp, bottom = 4.dp),
            text = coinSymbol,
            fontSize = 15.sp,
        )
    }
}

/*
* For the price and percentage */

@Composable
private fun CoinPrice(
    coinPriceUsd: String,
    coinChangePercent: String,
    hasIncreased: Boolean,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 2.dp, end = 2.dp, top = 7.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {

        ListDisplayText(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 4.dp, bottom = 4.dp),
            text = coinPriceUsd,
            fontSize = 15.sp
        )

        ListDisplayText(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 2.dp, bottom = 4.dp),
            text = coinChangePercent,
            fontSize = 15.sp,
            textColor = if (hasIncreased) Green else Red
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultPreview() {
    Box(modifier = Modifier.background(CoinsListBackground)) {
        CoinsList(
            coinsList = listOf(getPreviewCoin(), getPreviewCoin(), getPreviewCoin()),
            lazyColumnState = rememberLazyListState(),
            navigateToDetails = {}
        )
    }

}
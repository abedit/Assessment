package com.abedit.aldiassessment.ui.coinsOverview

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abedit.aldiassessment.NULL_VALUE_PLACEHOLDER
import com.abedit.aldiassessment.coinId2IconId
import com.abedit.aldiassessment.formattedPercentage
import com.abedit.aldiassessment.formattedPrice
import com.abedit.aldiassessment.getPreviewCoin
import com.abedit.aldiassessment.hasPriceIncreased
import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.ui.theme.CoinListItemBackground
import com.abedit.aldiassessment.ui.theme.Green
import com.abedit.aldiassessment.ui.theme.Red

@Composable
fun CoinsList(coinsList: List<Coin>, lazyColumnState: LazyListState) {
    //show the list of coins
    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 6.dp)
    ) {
        items(items = coinsList) { mCoin ->
            CoinListItem(mCoin = mCoin)

        }
    }
}

@Composable
private fun CoinListItem(mCoin: Coin) {
    Box(
        modifier = Modifier
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


            CoinDetails(
                coinId = mCoin.id,
                coinSymbol = mCoin.symbol ?: NULL_VALUE_PLACEHOLDER,
                modifier = Modifier.weight(1f)
            )

            CoinPrice(
                coinPriceUsd = mCoin.priceUsd.formattedPrice(),
                coinChangePercent = mCoin.changePercent24Hr.formattedPercentage(),
                hasIncreased = mCoin.changePercent24Hr.hasPriceIncreased,
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
        modifier = modifier
            .size(50.dp),
        tint = Color.Unspecified
    )
}

@Composable
private fun CoinDetails(
    coinId: String,
    coinSymbol: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 15.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = coinId,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 19.sp,
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp),
        )

        Text(
            text = coinSymbol,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(top = 2.dp, bottom = 4.dp),
        )
    }
}

@Composable
private fun CoinPrice(
    coinPriceUsd: String,
    coinChangePercent: String,
    hasIncreased: Boolean,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 2.dp, end = 2.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = coinPriceUsd,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 4.dp, bottom = 4.dp),

            )

        Text(
            text = coinChangePercent,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.End)
                .padding(top = 3.dp, bottom = 4.dp),
            color = if (hasIncreased) Green else Red
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultPreview() {
    CoinsList(
        coinsList = listOf(getPreviewCoin(), getPreviewCoin(), getPreviewCoin()),
        lazyColumnState = rememberLazyListState()
    )
}
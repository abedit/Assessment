package com.abedit.aldiassessment.ui.coinDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abedit.aldiassessment.R
import com.abedit.aldiassessment.formattedPercentage
import com.abedit.aldiassessment.hasPercentageIncreased
import com.abedit.aldiassessment.ui.sharedComponents.DetailDisplayItem
import com.abedit.aldiassessment.ui.theme.Blue
import com.abedit.aldiassessment.ui.theme.CoinsDetailsBackground
import com.abedit.aldiassessment.ui.theme.Green
import com.abedit.aldiassessment.ui.theme.Red

/*
* Details UI with the info
* */
@Composable
fun CoinDetailsOverview(
    price: String,
    percentChange: String,
    marketCap: String,
    volume24h: String,
    supply: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        /**
         * DisplayItem x 2
         * Divider
         * DisplayItem x 3
         * */

        DetailDisplayItem(
            caption = stringResource(R.string.coin_details_caption_price),
            value = price
        )
        DetailDisplayItem(
            caption = stringResource(R.string.coin_details_caption_change),
            value = percentChange.formattedPercentage(),
            textColor = if (percentChange.hasPercentageIncreased) Green else Red
        )

        HorizontalDivider(
            color = Blue,
            modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
        )

        DetailDisplayItem(
            caption = stringResource(R.string.coin_details_caption_market_cap),
            value = marketCap
        )
        DetailDisplayItem(
            caption = stringResource(R.string.coin_details_caption_volume_24h),
            value = volume24h
        )
        DetailDisplayItem(
            caption = stringResource(R.string.coin_details_caption_supply),
            value = supply
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultPreview() {
    Column(
        modifier = Modifier
            .background(CoinsDetailsBackground)
    ){
        CoinDetailsBox(
            roundedCornerShape = RoundedCornerShape(12.dp),
        ) {
            CoinDetailsOverview(
                price = "123",
                percentChange = "3.2",
                marketCap = "42",
                volume24h = "42",
                supply = "64"
            )
        }
    }


}
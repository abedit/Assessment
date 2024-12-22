package com.abedit.aldiassessment.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abedit.aldiassessment.NULL_VALUE_PLACEHOLDER
import com.abedit.aldiassessment.R
import com.abedit.aldiassessment.formattedPercentage
import com.abedit.aldiassessment.formattedPrice
import com.abedit.aldiassessment.getPreviewCoin
import com.abedit.aldiassessment.hasPercentageIncreased
import com.abedit.aldiassessment.states.DetailsUiState
import com.abedit.aldiassessment.ui.sharedComponents.DisplayItem
import com.abedit.aldiassessment.ui.sharedComponents.Toolbar
import com.abedit.aldiassessment.ui.theme.AldiAssessmentTheme
import com.abedit.aldiassessment.ui.theme.Blue
import com.abedit.aldiassessment.ui.theme.CoinDetailsItemBackground
import com.abedit.aldiassessment.ui.theme.CoinDetailsItemLoadingBackground
import com.abedit.aldiassessment.ui.theme.CoinDisplayText
import com.abedit.aldiassessment.ui.theme.CoinsDetailsBackground
import com.abedit.aldiassessment.ui.theme.Green
import com.abedit.aldiassessment.ui.theme.Red
import com.abedit.aldiassessment.ui.theme.ToolbarBackground
import com.abedit.aldiassessment.viewmodels.CoinDetailsViewModel


@Composable
fun CoinDetailsView(viewModel: CoinDetailsViewModel, backButtonClicked: () -> Unit) {
    val coin by viewModel.currentCoin.collectAsState()
    val detailsUiState by viewModel.uiState.collectAsState()

    CoinsDetailsView(
        detailsUiState = detailsUiState,
        coinName = coin.name ?: NULL_VALUE_PLACEHOLDER,
        price = coin.priceUsd.formattedPrice(),
        percentChange = coin.changePercent24Hr ?: NULL_VALUE_PLACEHOLDER,
        marketCap = coin.marketCapUsd.formattedPrice(),
        volume24h = coin.volumeUsd24Hr.formattedPrice(),
        supply = coin.supply.formattedPrice(),
        backButtonClicked = backButtonClicked
    )
}

@Composable
private fun CoinsDetailsView(
    detailsUiState: DetailsUiState,
    coinName: String,
    price: String,
    percentChange: String,
    marketCap: String,
    volume24h: String, supply: String,
    backButtonClicked: () -> Unit
) {
    AldiAssessmentTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                /*
                UI structure:
                * Toolbar
                * 2 x Row
                * Line
                * 3 x Row
                */
                Toolbar(
                    modifier = Modifier.background(ToolbarBackground),
                    title = coinName.uppercase(),
                    backButtonClicked = backButtonClicked
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CoinsDetailsBackground)
                ) {
                    CoinDetailsView(
                        detailsUiState = detailsUiState,
                        price = price,
                        percentChange = percentChange,
                        marketCap = marketCap,
                        volume24h = volume24h,
                        supply = supply
                    )
                }
            }
        }
    }
}


@Composable
private fun CoinDetailsView(
    detailsUiState: DetailsUiState,
    price: String, percentChange: String, marketCap: String,
    volume24h: String, supply: String,
) {

    val roundedCornerShape = RoundedCornerShape(12.dp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 30.dp)
            .background(CoinDetailsItemBackground, roundedCornerShape)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            DisplayItem(
                caption = stringResource(R.string.coin_details_caption_price),
                value = price
            )
            DisplayItem(
                caption = stringResource(R.string.coin_details_caption_change),
                value = percentChange.formattedPercentage(),
                textColor = if (percentChange.hasPercentageIncreased) Green else Red
            )

            HorizontalDivider(
                color = Blue,
                modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
            )

            DisplayItem(
                caption = stringResource(R.string.coin_details_caption_market_cap),
                value = marketCap
            )
            DisplayItem(
                caption = stringResource(R.string.coin_details_caption_volume_24h),
                value = volume24h
            )
            DisplayItem(
                caption = stringResource(R.string.coin_details_caption_supply),
                value = supply
            )
        }

        AnimatedVisibility(
            visible = detailsUiState == DetailsUiState.Loading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(CoinDetailsItemLoadingBackground, roundedCornerShape)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    strokeWidth = 3.dp,
                    color = Blue
                )
            }
        }
    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoinsOverviewPreview() {
    val previewCoin = getPreviewCoin()
    CoinsDetailsView(
        DetailsUiState.NotLoading,
        previewCoin.name?: NULL_VALUE_PLACEHOLDER,
        previewCoin.priceUsd.formattedPrice(),
        previewCoin.changePercent24Hr.formattedPercentage(),
        previewCoin.marketCapUsd.formattedPrice(),
        previewCoin.volumeUsd24Hr.formattedPrice(),
        previewCoin.supply.formattedPrice(),
        {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoinsOverviewLoadingPreview() {
    val previewCoin = getPreviewCoin()
    CoinsDetailsView(
        DetailsUiState.Loading,
        previewCoin.name?: NULL_VALUE_PLACEHOLDER,
        previewCoin.priceUsd.formattedPrice(),
        previewCoin.changePercent24Hr.formattedPercentage(),
        previewCoin.marketCapUsd.formattedPrice(),
        previewCoin.volumeUsd24Hr.formattedPrice(),
        previewCoin.supply.formattedPrice(),
        {}
    )
}


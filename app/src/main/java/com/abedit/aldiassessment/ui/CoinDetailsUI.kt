package com.abedit.aldiassessment.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abedit.aldiassessment.NULL_VALUE_PLACEHOLDER
import com.abedit.aldiassessment.formattedPercentage
import com.abedit.aldiassessment.formattedPrice
import com.abedit.aldiassessment.getPreviewCoin
import com.abedit.aldiassessment.states.DetailsUiState
import com.abedit.aldiassessment.ui.coinDetailsComponents.CoinDetailsBox
import com.abedit.aldiassessment.ui.coinDetailsComponents.CoinDetailsError
import com.abedit.aldiassessment.ui.coinDetailsComponents.CoinDetailsOverview
import com.abedit.aldiassessment.ui.sharedComponents.Toolbar
import com.abedit.aldiassessment.ui.theme.AldiAssessmentTheme
import com.abedit.aldiassessment.ui.theme.Blue
import com.abedit.aldiassessment.ui.theme.CoinDetailsItemLoadingBackground
import com.abedit.aldiassessment.ui.theme.CoinsDetailsBackground
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
        backButtonClicked = backButtonClicked,
        tryAgainClicked = { viewModel.fetchCoinInfo() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CoinsDetailsView(
    detailsUiState: DetailsUiState,
    coinName: String,
    price: String,
    percentChange: String,
    marketCap: String,
    volume24h: String, supply: String,
    backButtonClicked: () -> Unit,
    tryAgainClicked: () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

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
                * CoinDetailsView
                *   2 x Row
                *   Line
                *   3 x Row
                */
                Toolbar(
                    modifier = Modifier.background(ToolbarBackground),
                    title = coinName.uppercase(),
                    backButtonClicked = backButtonClicked
                )


//                LaunchedEffect(detailsUiState) {
//                    isRefreshing = false
//                }

                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        isRefreshing = true
                        tryAgainClicked.invoke()
                    },
                    state = pullToRefreshState,
                ) {
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
                            supply = supply,
                            tryAgainClicked = tryAgainClicked
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CoinDetailsView(
    detailsUiState: DetailsUiState,
    price: String, percentChange: String, marketCap: String,
    volume24h: String, supply: String,
    tryAgainClicked: () -> Unit
) {

    val roundedCornerShape = RoundedCornerShape(12.dp)

    /*
    * Show the details overview
    * if loading -> keep the details overview visible but overlay the loading view
    * if error -> only show the error view and hide the others
    * */

    //Error view
    AnimatedVisibility(
        visible = detailsUiState == DetailsUiState.Error,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CoinDetailsBox(
            roundedCornerShape = roundedCornerShape,
            contentAlignment = Alignment.Center
        ) {
            CoinDetailsError(tryAgainClicked = tryAgainClicked)
        }
    }


    //Main details view
    AnimatedVisibility(
        visible = detailsUiState != DetailsUiState.Error,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        CoinDetailsBox(
            roundedCornerShape = roundedCornerShape,
        ) {

            CoinDetailsOverview(
                price = price,
                percentChange = percentChange,
                marketCap = marketCap,
                volume24h = volume24h,
                supply = supply
            )
        }
    }


    //Loading view
    AnimatedVisibility(
        visible = detailsUiState == DetailsUiState.Loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CoinDetailsBox(
            modifier = Modifier.background(CoinDetailsItemLoadingBackground),
            contentAlignment = Alignment.Center,
            roundedCornerShape = roundedCornerShape,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                strokeWidth = 3.dp,
                color = Blue
            )
        }
    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoinsOverviewPreview() {
    val previewCoin = getPreviewCoin()
    CoinsDetailsView(
        DetailsUiState.NotLoading,
        previewCoin.name ?: NULL_VALUE_PLACEHOLDER,
        previewCoin.priceUsd.formattedPrice(),
        previewCoin.changePercent24Hr.formattedPercentage(),
        previewCoin.marketCapUsd.formattedPrice(),
        previewCoin.volumeUsd24Hr.formattedPrice(),
        previewCoin.supply.formattedPrice(),
        {}
    ) {}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoinsOverviewLoadingPreview() {
    val previewCoin = getPreviewCoin()
    CoinsDetailsView(
        DetailsUiState.Loading,
        previewCoin.name ?: NULL_VALUE_PLACEHOLDER,
        previewCoin.priceUsd.formattedPrice(),
        previewCoin.changePercent24Hr.formattedPercentage(),
        previewCoin.marketCapUsd.formattedPrice(),
        previewCoin.volumeUsd24Hr.formattedPrice(),
        previewCoin.supply.formattedPrice(),
        {}
    ) {}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoinsOverviewErrorPreview() {
    CoinsDetailsView(
        DetailsUiState.Error,
        "", "", "", "", "", "",
        {}
    ) {}
}


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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abedit.aldiassessment.R
import com.abedit.aldiassessment.getPreviewCoin
import com.abedit.aldiassessment.overviewData.ListUiState
import com.abedit.aldiassessment.ui.coinsOverview.CoinsList
import com.abedit.aldiassessment.ui.coinsOverview.EmptyListView
import com.abedit.aldiassessment.ui.sharedComponents.Toolbar
import com.abedit.aldiassessment.ui.theme.AldiAssessmentTheme
import com.abedit.aldiassessment.ui.theme.Blue
import com.abedit.aldiassessment.ui.theme.CoinsListBackground
import com.abedit.aldiassessment.ui.theme.ToolbarBackground
import com.abedit.aldiassessment.viewmodels.CoinListViewModel

@Composable
fun CoinsOverview(coinListViewModel: CoinListViewModel) {
    val listUiState by coinListViewModel.uiState.collectAsState()
    CoinsOverview(listUiState = listUiState, tryAgainAction = { coinListViewModel.fetchCoins() })
}

@Composable
fun CoinsOverview(listUiState: ListUiState, tryAgainAction: () -> Unit = {}) {

    val snackbarHostState = remember { SnackbarHostState() }

    //would like to save the scroll position after automatic refresh
    val lazyColumnState = rememberLazyListState()

    AldiAssessmentTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                /*
                UI structure:
                * Toolbar
                * CoinsList
                */
                Toolbar(
                    modifier = Modifier.background(ToolbarBackground),
                    title = stringResource(R.string.coins_overview_toolbar_title),
                    showBackButton = false
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CoinsListBackground)
                ) {
                    CoinsView(
                        listUiState = listUiState,
                        tryAgainAction = tryAgainAction,
                        lazyColumnState = lazyColumnState)
                }

            }
        }

    }

    //Show snackbar based on the UIState
    val context = LocalContext.current
    LaunchedEffect(listUiState) {
        if (listUiState is ListUiState.Error) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.api_fetch_error_message),
                duration = SnackbarDuration.Short
            )
        } else if (listUiState is ListUiState.ErrorListNotEmpty) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.api_fetch_error_message_with_data),
                duration = SnackbarDuration.Short
            )
        }
    }
}

@Composable
private fun CoinsView(
    listUiState: ListUiState,
    tryAgainAction: () -> Unit,
    lazyColumnState: LazyListState
) {

    // show progress indicator if list is empty
    AnimatedVisibility(
        visible = listUiState is ListUiState.Loading,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                strokeWidth = 3.dp,
                color = Blue
            )
        }
    }

    //other scenarios - if list has data or is empty
    AnimatedVisibility(
        visible = listUiState !is ListUiState.Loading,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        when (listUiState) {
            is ListUiState.Success -> CoinsList(coinsList = listUiState.items, lazyColumnState = lazyColumnState)
            is ListUiState.ErrorListNotEmpty -> CoinsList(coinsList = listUiState.items, lazyColumnState = lazyColumnState)
            is ListUiState.Empty -> EmptyListView(tryAgainAction = tryAgainAction)
            else -> {} //Loading or Error - already handled
        }
    }

}






@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoinsOverviewSuccessPreview() {
    CoinsOverview(listUiState = ListUiState.Success(listOf(
        getPreviewCoin(),
        getPreviewCoin(),
        getPreviewCoin(),
    )))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoinsOverviewLoadingPreview() {
    CoinsOverview(listUiState = ListUiState.Loading)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoinsOverviewEmptyPreview() {
    CoinsOverview(listUiState = ListUiState.Empty)
}